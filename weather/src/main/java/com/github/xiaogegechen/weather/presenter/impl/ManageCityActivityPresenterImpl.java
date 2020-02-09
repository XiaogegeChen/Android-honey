package com.github.xiaogegechen.weather.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.Constants;
import com.github.xiaogegechen.common.arouter.ARouterMap;
import com.github.xiaogegechen.common.network.Callback;
import com.github.xiaogegechen.common.network.CheckHelper;
import com.github.xiaogegechen.common.util.ListUtils;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.weather.Api;
import com.github.xiaogegechen.weather.helper.SelectedCitiesManager;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.SelectedCityForRvInMCAct;
import com.github.xiaogegechen.weather.model.db.SelectedCity;
import com.github.xiaogegechen.weather.model.event.NotifyCityRemovedEvent;
import com.github.xiaogegechen.weather.model.event.NotifySelectedCityRvInMCActItemClickEvent;
import com.github.xiaogegechen.weather.model.json.NowJSON;
import com.github.xiaogegechen.weather.presenter.IManageCityActivityPresenter;
import com.github.xiaogegechen.weather.view.IManageCityActivityView;
import com.github.xiaogegechen.weather.view.impl.SelectCityActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageCityActivityPresenterImpl implements IManageCityActivityPresenter {
    private static final String TAG = "ManageCityActivityPrese";

    private IManageCityActivityView mManageCityActivityView;
    private Activity mActivity;

    // 当前列表中显示的城市的集合，这个list的内容和界面中recyclerView的数据源时刻保持一致的
    private List<SelectedCity> mCurrCityList;

    private Retrofit mHWeatherRetrofit;
    private Call<NowJSON> mWeatherNowCall;

    @Override
    public void attach(IManageCityActivityView manageCityActivityView) {
        mManageCityActivityView = manageCityActivityView;
        mActivity = (Activity) mManageCityActivityView;
        mCurrCityList = new ArrayList<>();
        mHWeatherRetrofit = new Retrofit.Builder()
                .baseUrl(com.github.xiaogegechen.weather.Constants.WEATHER_QUERY_BASIC_WEATHER_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void detach() {
        RetrofitHelper.cancelCalls(mWeatherNowCall);
        mManageCityActivityView = null;
    }

    @Override
    public void gotoSelectCityActivity() {
        Intent intent = new Intent(mActivity, SelectCityActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    public void querySelectedCity() {
        // 重新查询数据库，拿到最新的城市列表
        List<SelectedCity> newestSelectedCityList = SelectedCitiesManager.getInstance().getSelectedCities();
        if(newestSelectedCityList != null && newestSelectedCityList.size() > 0){
            // 先把recyclerView中多余的项找到并删除
            List<SelectedCity> onlyInRv = ListUtils.onlyInFirst(mCurrCityList, newestSelectedCityList);
            // 统一移除
            if(onlyInRv.size() > 0){
                for (SelectedCity shouldRemovedCity : onlyInRv) {
                    mManageCityActivityView.removeItem(shouldRemovedCity.getCityId());
                    mCurrCityList.remove(shouldRemovedCity);
                }
            }
            // 再把数据库中需要添加的数据都找到并添加
            final List<SelectedCity> onlyInDb = ListUtils.onlyInFirst(newestSelectedCityList, mCurrCityList);
            // 统一添加，但是每个条目只有城市名，没有天气信息
            if(onlyInDb.size() > 0){
                for (SelectedCity shouldAddedCity : onlyInDb) {
                    SelectedCityForRvInMCAct sc = new SelectedCityForRvInMCAct();
                    sc.setId(shouldAddedCity.getCityId());
                    sc.setLocation(shouldAddedCity.getLocation());
                    mManageCityActivityView.addItem(sc);
                    mCurrCityList.add(makeOnlyHasCityIdSelectedCity(shouldAddedCity.getCityId()));
                }
            }
            // 追加天气信息
            if(onlyInDb.size() > 0){
                new Thread(() -> queryWeatherInfoOfNewAddedCityAndChangeUISync(onlyInDb)).start();
            }
        }else{
            // 没有添加任何城市
            mManageCityActivityView.showNothing();
        }
    }

    /**
     * 串行请求每一个新加的城市并更新UI，这个方法是阻塞方法，需要放在子线程执行
     * @param newAddedCities 新添加的城市列表
     */
    private void queryWeatherInfoOfNewAddedCityAndChangeUISync(List<SelectedCity> newAddedCities){
        for (final SelectedCity newAddedCity : newAddedCities) {
            logInfo("begin to query weather info of city whose location is " + newAddedCity.getLocation());
            mWeatherNowCall = mHWeatherRetrofit.create(Api.class)
                    .queryWeatherNow(
                            com.github.xiaogegechen.weather.Constants.WEATHER_KEY,
                            newAddedCity.getCityId());
            try {
                Response<NowJSON> response = mWeatherNowCall.execute();
                if (!mWeatherNowCall.isCanceled()){
                    final NowJSON body = response.body();
                    if (body != null) {
                        CheckHelper.checkResultFromHWeatherServer(body, new Callback() {
                            @Override
                            public void onSuccess() {
                                logInfo("query success");
                                mActivity.runOnUiThread(() -> {
                                    NowJSON.Result.Now now = body.getResult().get(0).getNow();
                                    // 更新这个City的天气数据
                                    SelectedCityForRvInMCAct selectedCityForRvInMCAct = new SelectedCityForRvInMCAct();
                                    selectedCityForRvInMCAct.setId(newAddedCity.getCityId());
                                    selectedCityForRvInMCAct.setLocation(newAddedCity.getLocation());
                                    selectedCityForRvInMCAct.setTemp(now.getTmp());
                                    selectedCityForRvInMCAct.setWeatherDescription(now.getCondDescription());
                                    selectedCityForRvInMCAct.setWeatherCode(now.getCondCode());
                                    mManageCityActivityView.changeItem(selectedCityForRvInMCAct);
                                });
                            }

                            @Override
                            public void onFailure(String errorCode, String errorMessage) {
                                logError("failed check when query weather of city whose location is " + newAddedCity.getLocation() + ", errorCode -> " + errorCode + ", errorMsg -> " + errorMessage);
                            }
                        });
                    }else{
                        // body 是空的，是未知错误
                        logError("body is null when query weather of city whose location is " + newAddedCity.getLocation());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                logError("meet IOException when query weather of city whose location is " + newAddedCity.getLocation() + ", exception -> " + e.getMessage());
            }
        }
    }

    @Override
    public void handleNotifyCityRemovedEvent(NotifyCityRemovedEvent event) {
        if(event.getFlag() == NotifyCityRemovedEvent.FLAG_FROM_MANAGE_CITY_ACTIVITY){
            CityInfo cityInfo = event.getCityInfo();
            // 先移除city
            SelectedCitiesManager.getInstance().removeCity(cityInfo);
            // 再移除recyclerView中对应的条目
            String cityId = cityInfo.getCityId();
            removeCityFromCurrCityList(cityId);
            mManageCityActivityView.removeItem(cityId);
        }
    }

    private void removeCityFromCurrCityList(String cityId){
        for (int i = 0; i < mCurrCityList.size(); i++) {
            if(cityId.equals(mCurrCityList.get(i).getCityId())){
                mCurrCityList.remove(i);
                break;
            }
        }
    }

    @Override
    public void handleNotifySelectedCityRvInMCActItemClickEvent(NotifySelectedCityRvInMCActItemClickEvent event) {
        String cityId = event.getId();
        // 回到主界面
        CityInfo cityInfoOnlyHasCityId = new CityInfo();
        cityInfoOnlyHasCityId.setCityId(cityId);
        ARouter.getInstance()
                .build(ARouterMap.MAIN_MAIN_ACTIVITY)
                // 这里不需要携带数据
                .withParcelableArray(Constants.INTENT_PARAM_FROM_MANAGE_CITY_ACTIVITY, new CityInfo[]{cityInfoOnlyHasCityId})
                .navigation();
    }

    @Override
    public void finish() {
        List<SelectedCity> selectedCities = SelectedCitiesManager.getInstance().getSelectedCities();
        if(selectedCities == null || selectedCities.size() == 0){
            mManageCityActivityView.showToast("请先选择一个城市");
        }else{
            // 回到主界面
            ARouter.getInstance()
                    .build(ARouterMap.MAIN_MAIN_ACTIVITY)
                    // 这里不需要携带数据
                    .withParcelableArray(Constants.INTENT_PARAM_FROM_MANAGE_CITY_ACTIVITY, new CityInfo[]{new CityInfo()})
                    .navigation();
        }
    }

    /**
     * 只包含城市id的{@link SelectedCity} 实例
     * @param cityId 城市id
     * @return 只包含城市id的 SelectedCity 实例
     */
    private static SelectedCity makeOnlyHasCityIdSelectedCity(String cityId){
        SelectedCity selectedCityOnlyHasCityId = new SelectedCity();
        selectedCityOnlyHasCityId.setCityId(cityId);
        return selectedCityOnlyHasCityId;
    }

    private static void logError(String msg){
        LogUtil.e(TAG, msg);
    }

    private static void logInfo(String msg){
        LogUtil.i(TAG, msg);
    }
}
