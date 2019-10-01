package com.github.xiaogegechen.module_d.presenter.impl;

import android.content.Context;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.common.util.XmlIOUtil;
import com.github.xiaogegechen.module_d.Api;
import com.github.xiaogegechen.module_d.Constants;
import com.github.xiaogegechen.module_d.ModuleDApplication;
import com.github.xiaogegechen.module_d.model.CatalogInfo;
import com.github.xiaogegechen.module_d.model.CatalogJSON;
import com.github.xiaogegechen.module_d.model.db.CatalogInfoInDB;
import com.github.xiaogegechen.module_d.model.db.CatalogInfoInDBDao;
import com.github.xiaogegechen.module_d.model.db.DaoSession;
import com.github.xiaogegechen.module_d.presenter.IBookListActivityPresenter;
import com.github.xiaogegechen.module_d.view.IBookListActivityView;

import org.greenrobot.greendao.query.Query;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// NOTE: dialog的状态统一由activity管理，不由该类管理
// 比如activity会完成在showErrorPage()时关闭progressDialog等

public class BookListActivityPresenterImpl implements IBookListActivityPresenter {

    private static final String TAG = "BookListActivityPresent";

    private IBookListActivityView mBookListActivityView;
    private Context mContext;

    // 网络请求相关
    private Retrofit mJuheRetrofit;
    private Call<CatalogJSON> mCatalogCall;

    // 数据库相关
    private CatalogInfoInDBDao mCatalogInfoDao;

    public BookListActivityPresenterImpl(Context context) {
        mContext = context;
        // CatalogInfo表
        DaoSession daoSession = ModuleDApplication.getDaoSession();
        mCatalogInfoDao = daoSession.getCatalogInfoInDBDao();
    }

    @Override
    public void attach(@Nullable IBookListActivityView bookListActivityView) {
        mBookListActivityView = bookListActivityView;
        mJuheRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.JUHE_BOOK_BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void detach() {
        // 取消网络请求，释放资源
        RetrofitHelper.cancelCall(mCatalogCall);
        mBookListActivityView = null;
    }

    @Override
    public void queryCatalog() {
        mBookListActivityView.showProgress();
        // 先判断上次请求有没有超过规定时间
        long lastTime = XmlIOUtil.INSTANCE.readLong(Constants.XML_KEY_CATALOG_SESSION_MODULE_D, mContext);
        long currentTime = System.currentTimeMillis();
        // 初始为0值，要特殊考虑，这时只能请求网络并存入数据库中
        if(lastTime != 0 && ((currentTime - lastTime) <= Constants.CATALOG_SESSION)){
            // 直接从数据库中取来就好了
            Query<CatalogInfoInDB> queryAll = mCatalogInfoDao.queryBuilder().build();
            List<CatalogInfoInDB> catalogInfoInDBList = queryAll.list();
            if(catalogInfoInDBList != null && catalogInfoInDBList.size() > 0){
                List<CatalogInfo> catalogInfoList = convertCatalogInfoInDB2CatalogInfo(catalogInfoInDBList);
                mBookListActivityView.showCatalog(catalogInfoList);
            }else{
                mBookListActivityView.showToast(Constants.UNKNOWN_ERROR);
            }
            mBookListActivityView.hideProgress();
        }else{
            // 从网络拿到并保存在数据库中
            mCatalogCall = mJuheRetrofit.create(Api.class).queryCatalog(Constants.JUHE_BOOK_ACCENT_KEY, "json");
            mCatalogCall.enqueue(new Callback<CatalogJSON>() {
                @Override
                public void onResponse(@NotNull Call<CatalogJSON> call, @NotNull Response<CatalogJSON> response) {
                    LogUtil.d(TAG, "query catalog success");
                    CatalogJSON catalogJSON = response.body();
                    if (catalogJSON != null) {
                        List<CatalogInfo> catalogInfoList = catalogJSON.getResult();
                        if(catalogInfoList != null && catalogInfoList.size() > 0){
                            // 先显示
                            mBookListActivityView.showCatalog(catalogInfoList);
                            // 再存进数据库中
                            for (CatalogInfo catalogInfo : catalogInfoList) {
                                CatalogInfoInDB catalogInfoInDB = new CatalogInfoInDB(catalogInfo.getCatalog(), catalogInfo.getId());
                                mCatalogInfoDao.insertOrReplace(catalogInfoInDB);
                            }
                            // 更新存储时间
                            long refreshTime = System.currentTimeMillis();
                            XmlIOUtil.INSTANCE.writeLong(Constants.XML_KEY_CATALOG_SESSION_MODULE_D, refreshTime, mContext);
                        }else{
                            mBookListActivityView.showToast(Constants.UNKNOWN_ERROR);
                        }
                    }else{
                        mBookListActivityView.showToast(Constants.UNKNOWN_ERROR);
                    }
                    mBookListActivityView.hideProgress();
                }

                @Override
                public void onFailure(@NotNull Call<CatalogJSON> call, @NotNull Throwable t) {
                    LogUtil.d(TAG, "query catalog failed! error is : " + t.toString());
                    // 如果请求取消了，就意味着activity销毁了，那么dialog不需要管理，因此不用考虑取消
                    // 时候的dialog
                    if(!call.isCanceled()){
                        // 加载失败，显示错误dialog
                        mBookListActivityView.showErrorPage();
                        mBookListActivityView.hideProgress();
                    }
                }
            });
        }
    }

    @Override
    public void queryBookList(int catalogId) {

    }

    @Override
    public void retry() {

    }

    private static List<CatalogInfo> convertCatalogInfoInDB2CatalogInfo(List<CatalogInfoInDB> catalogInfoInDBList){
        List<CatalogInfo> catalogInfoList = new ArrayList<>();
        for (CatalogInfoInDB catalogInfoInDB : catalogInfoInDBList) {
            CatalogInfo catalogInfo = new CatalogInfo(catalogInfoInDB.getCatalog(), catalogInfoInDB.getCatalogId());
            catalogInfoList.add(catalogInfo);
        }
        return catalogInfoList;
    }
}
