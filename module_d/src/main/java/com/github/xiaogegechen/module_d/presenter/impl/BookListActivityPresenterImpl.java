package com.github.xiaogegechen.module_d.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.test.TestActivity;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.common.util.XmlIOUtil;
import com.github.xiaogegechen.module_d.Api;
import com.github.xiaogegechen.module_d.Constants;
import com.github.xiaogegechen.module_d.ModuleDApplication;
import com.github.xiaogegechen.module_d.model.BookListJSON;
import com.github.xiaogegechen.module_d.model.CatalogInfo;
import com.github.xiaogegechen.module_d.model.CatalogJSON;
import com.github.xiaogegechen.module_d.model.db.BookInDB;
import com.github.xiaogegechen.module_d.model.db.BookInDBDao;
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

    private static final int MSG_BOOK_LIST_SUCCESS = 100;
    private static final int MSG_BOOK_LIST_FAILED = 101;

    private IBookListActivityView mBookListActivityView;
    private Context mContext;

    // 网络请求相关
    private Retrofit mJuheRetrofit;
    private Call<CatalogJSON> mCatalogCall;
    private Call<BookListJSON> mBookListCall;

    // 数据库相关
    private CatalogInfoInDBDao mCatalogInfoDao;
    private BookInDBDao mBookDao;

    // 专门处理bookList的主线程handler
    private Handler mBookListHandler;

    public BookListActivityPresenterImpl(Context context) {
        mContext = context;
        DaoSession daoSession = ModuleDApplication.getDaoSession();
        // CatalogInfo表
        mCatalogInfoDao = daoSession.getCatalogInfoInDBDao();
        // Book表
        mBookDao = daoSession.getBookInDBDao();

        mBookListHandler = new Handler(mContext.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case MSG_BOOK_LIST_SUCCESS:
                        @SuppressWarnings("unchecked")
                        List<BookInDB> bookList = (List<BookInDB>) msg.obj;
                        int catalogId = msg.arg1;
                        // 先显示
                        mBookListActivityView.showBookList(bookList);
                        // 存进数据库
                        Query<BookInDB> queryAll = mBookDao.queryBuilder().where(BookInDBDao.Properties.CatalogId.eq(catalogId)).build();
                        List<BookInDB> oldList = queryAll.list();
                        for (BookInDB bookInDB : oldList) {
                            mBookDao.delete(bookInDB);
                        }
                        for (BookInDB bookInDB : bookList) {
                            mBookDao.insertOrReplace(bookInDB);
                        }
                        // 更新存储时间
                        long refreshTime = System.currentTimeMillis();
                        XmlIOUtil.INSTANCE.writeLong(getKey(catalogId), refreshTime, mContext);
                        mBookListActivityView.hideProgress();
                        break;
                    case MSG_BOOK_LIST_FAILED:
                        // 加载失败，显示错误dialog
                        mBookListActivityView.hideProgress();
                        mBookListActivityView.showErrorPage();
                        break;
                    default:
                        break;
                }
            }
        };
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
        RetrofitHelper.cancelCalls(mCatalogCall, mBookListCall);
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
            LogUtil.i(TAG, "query catalog from database");
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
            LogUtil.i(TAG, "query catalog from network");
            // 从网络拿到并保存在数据库中
            mCatalogCall = mJuheRetrofit.create(Api.class).queryCatalog(Constants.JUHE_BOOK_ACCENT_KEY, "json");
            mCatalogCall.enqueue(new Callback<CatalogJSON>() {
                @Override
                public void onResponse(@NotNull Call<CatalogJSON> call, @NotNull Response<CatalogJSON> response) {
                    LogUtil.i(TAG, "query catalog from network success");
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
                    LogUtil.i(TAG, "query catalog from network failed, error is : " + t.toString());
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
    public void queryBookList(final int catalogId) {
        mBookListActivityView.showProgress();
        // 先判断上次请求有没有超过规定时间
        long lastTime = XmlIOUtil.INSTANCE.readLong(getKey(catalogId), mContext);
        long currentTime = System.currentTimeMillis();
        // 初始为0值，要特殊考虑，这时只能请求网络并存入数据库中
        if(lastTime != 0 && ((currentTime - lastTime) <= Constants.BOOK_LIST_SESSION)){
            LogUtil.i(TAG, "query book list from database, catalogId is :" + catalogId);
            Query<BookInDB> queryAll = mBookDao.queryBuilder().where(BookInDBDao.Properties.CatalogId.eq(catalogId)).build();
            List<BookInDB> bookInDBList = queryAll.list();
            if(bookInDBList != null && bookInDBList.size() > 0){
                LogUtil.d(TAG, "query book list from database, catalogId is :" + catalogId + ", bookList size is : " + bookInDBList.size());
                mBookListActivityView.showBookList(bookInDBList);
            }else{
                mBookListActivityView.showToast(Constants.UNKNOWN_ERROR);
            }
            mBookListActivityView.hideProgress();
        }else{
            // TODO 改用线程池
            // 新开线程请求图书列表
            LogUtil.i(TAG, "query book list from network, catalogId is :" + catalogId);
            new Thread(() -> {
                try {
                    final List<BookInDB> bookList = queryBookListFromNetwork(catalogId);
                    if(bookList.size() > 0){
                        // 回到主线程做显示和写入数据库
                        LogUtil.i(TAG, "query book list from network success");
                        Message message = mBookListHandler.obtainMessage();
                        message.what = MSG_BOOK_LIST_SUCCESS;
                        message.arg1 = catalogId;
                        message.obj = bookList;
                        mBookListHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    LogUtil.i(TAG, "query book list from network failed, error is : " + e.toString());
                    Message message = mBookListHandler.obtainMessage();
                    message.what = MSG_BOOK_LIST_FAILED;
                    mBookListHandler.sendMessage(message);
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public void retryCatalog() {
        queryCatalog();
    }

    @Override
    public void retryBookList(int catalogId) {
        queryBookList(catalogId);
    }

    // 这个方法需要在子线程中执行，因为用的是同步请求网络
    private List<BookInDB> queryBookListFromNetwork(int catalogId) throws Exception{
        final List<BookInDB> result = new ArrayList<>();
        mBookListCall = mJuheRetrofit.create(Api.class).queryBookList(
                Constants.JUHE_BOOK_ACCENT_KEY, catalogId, 0, 30);
        Response<BookListJSON> response = mBookListCall.execute();
        BookListJSON bookListJSON = response.body();
        if(bookListJSON != null){
            int totalNum = Integer.parseInt(bookListJSON.getResult().getTotalNum());
            // 先存入数据库
            List<BookListJSON.Data> dataList = bookListJSON.getResult().getData();
            result.addAll(convertData2BookInDB(dataList, catalogId));
            // 已经拿到的图书数量
            if(totalNum <= 30){
                // 没有更多数据了，直接返回
                return result;
            }else{
                int loadedCount = 30;
                // 递归调用，拿到所有数据
                while(loadedCount < totalNum){
                    List<BookInDB> bookList = rQueryBookListFromNetwork(catalogId, loadedCount);
                    result.addAll(bookList);
                    loadedCount = loadedCount + bookList.size();
                }
            }
        }
        return result;
    }

    // 递归体
    private List<BookInDB> rQueryBookListFromNetwork(int catalogId, int offset) throws Exception{
        List<BookInDB> result = new ArrayList<>();
        mBookListCall = mJuheRetrofit.create(Api.class).queryBookList(
                Constants.JUHE_BOOK_ACCENT_KEY, catalogId, offset, 30);
        Response<BookListJSON> response = mBookListCall.execute();
        BookListJSON bookListJSON = response.body();
        if(bookListJSON != null){
            List<BookListJSON.Data> dataList = bookListJSON.getResult().getData();
            result.addAll(convertData2BookInDB(dataList, catalogId));
        }
        return result;
    }

    // 为了区分不同目录下图书的刷新时间在sp中的位置，通过key+"_"+catalogId形式
    // 拼接出key
    private static String getKey(int catalogId){
        return Constants.XML_KEY_BOOK_LIST_SESSION_MODULE_D + "_" + catalogId;
    }

    private static List<CatalogInfo> convertCatalogInfoInDB2CatalogInfo(List<CatalogInfoInDB> catalogInfoInDBList){
        List<CatalogInfo> catalogInfoList = new ArrayList<>();
        for (CatalogInfoInDB catalogInfoInDB : catalogInfoInDBList) {
            CatalogInfo catalogInfo = new CatalogInfo(catalogInfoInDB.getCatalog(), catalogInfoInDB.getCatalogId());
            catalogInfoList.add(catalogInfo);
        }
        return catalogInfoList;
    }

    private static List<BookInDB> convertData2BookInDB(List<BookListJSON.Data> dataList, int catalogId){
        List<BookInDB> result = new ArrayList<>();
        for (BookListJSON.Data data : dataList) {
            BookInDB bookInDB = new BookInDB();
            bookInDB.setCatalogId(catalogId);
            bookInDB.setCatalog(data.getCatalog());
            bookInDB.setBytime(data.getBytime());
            bookInDB.setImg(data.getImg());
            bookInDB.setOnline(data.getOnline());
            bookInDB.setReading(data.getReading());
            bookInDB.setSub1(data.getSub1());
            bookInDB.setSub2(data.getSub2());
            bookInDB.setTags(data.getTags());
            bookInDB.setTitle(data.getTitle());
            result.add(bookInDB);
        }
        return result;
    }

    @Override
    public void debug() {
        Query<BookInDB> query = mBookDao.queryBuilder().build();
        List<BookInDB> list = query.list();
        TestActivity.startDebug((Activity) mContext, list.size() + " " + list.toString());
    }
}
