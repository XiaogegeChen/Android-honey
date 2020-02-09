package com.github.xiaogegechen.weather.presenter.impl;

import android.text.TextUtils;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.network.CheckHelper;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.weather.Api;
import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.helper.SelectedCitiesManager;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.json.CityListJSON;
import com.github.xiaogegechen.weather.model.json.TopCityJSON;
import com.github.xiaogegechen.weather.presenter.ISelectCityActivityPresenter;
import com.github.xiaogegechen.weather.view.ISelectCityActivityView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectCityActivityPresenterImpl implements ISelectCityActivityPresenter {

    // 匹配中文字符的正则表达式
    private static Pattern sPattern = Pattern.compile("[\\u4e00-\\u9fa5]");

    private ISelectCityActivityView mSelectCityActivityView;

    // retrofit相关
    private Retrofit mSearchCityRetrofit;
    private Call<TopCityJSON> mTopCityCall;
    private Call<CityListJSON> mCityListCall;

    @Override
    public void attach(ISelectCityActivityView selectCityActivityView) {
        mSelectCityActivityView = selectCityActivityView;
        // retrofit
        mSearchCityRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEATHER_SEARCH_CITY_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void detach() {
        RetrofitHelper.cancelCalls(mTopCityCall, mCityListCall);
        mSelectCityActivityView = null;
    }

    @Override
    public void queryTopCityList() {
        mTopCityCall = mSearchCityRetrofit.create(Api.class).queryTopCityList(Constants.WEATHER_KEY, "cn");
        mTopCityCall.enqueue(new Callback<TopCityJSON>() {
            @Override
            public void onResponse(@NotNull Call<TopCityJSON> call, @NotNull Response<TopCityJSON> response) {
                final TopCityJSON body = response.body();
                if (body != null) {
                    CheckHelper.checkResultFromHWeatherServer(body, new com.github.xiaogegechen.common.network.Callback() {
                        @Override
                        public void onSuccess() {
                            List<CityInfo> cityInfoList = convertTopCityJSON2CityInfoList(body);
                            mSelectCityActivityView.showTopCityList(cityInfoList);
                        }

                        @Override
                        public void onFailure(String errorCode, String errorMessage) {
                            mSelectCityActivityView.showToast("未查询到热门城市");
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NotNull Call<TopCityJSON> call, @NotNull Throwable t) {
                if (!call.isCanceled()){
                    mSelectCityActivityView.showToast("查询热门城市失败");
                }
            }
        });
    }

    @Override
    public void removeCity(CityInfo cityInfo) {
        SelectedCitiesManager.getInstance().removeCity(cityInfo);
    }

    @Override
    public void addCity(CityInfo cityInfo) {
        SelectedCitiesManager.getInstance().addCity(cityInfo);
    }

    @Override
    public void handleInput(String text) {
        if(TextUtils.isEmpty(text)){
            // 显示热门城市列表
            mSelectCityActivityView.showTopCityList();
        }else{
            // 筛选中文进行模糊搜索
            String chinese = remainChinese(text);
            queryCityList(chinese);
        }
    }

    @Override
    public void queryCityList(String input) {
        if(!TextUtils.isEmpty(input)){
            mCityListCall = mSearchCityRetrofit.create(Api.class).queryCityList(Constants.WEATHER_KEY, "cn", input);
            mCityListCall.enqueue(new Callback<CityListJSON>() {
                @Override
                public void onResponse(@NotNull Call<CityListJSON> call, @NotNull Response<CityListJSON> response) {
                    final CityListJSON body = response.body();
                    if (body != null) {
                        CheckHelper.checkResultFromHWeatherServer(body, new com.github.xiaogegechen.common.network.Callback() {
                            @Override
                            public void onSuccess() {
                                List<CityInfo> cityInfoList = convertCityListJSON2CityInfoList(body);
                                mSelectCityActivityView.showCityList(cityInfoList);
                            }

                            @Override
                            public void onFailure(String errorCode, String errorMessage) {
                                mSelectCityActivityView.showToast("没有查询到相关城市");
                            }
                        });
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CityListJSON> call, @NotNull Throwable t) {
                    if(!call.isCanceled()){
                        mSelectCityActivityView.showToast("没有查询到相关城市");
                    }
                }
            });
        }
    }

    /**
     * 对拿到的json数据进行转化，转化为城市列表。
     * 从sp中拿到已经添加的城市的城市代码，将已经添加的城市selected字段设置为true
     *
     * @param topCityJSON 请求的json数据
     * @return 城市列表，已经设置了 selected 字段
     */
    private List<CityInfo> convertTopCityJSON2CityInfoList(TopCityJSON topCityJSON){
        List<CityInfo> result = topCityJSON.getResult().get(0).getCityInfoList();
        if(!SelectedCitiesManager.getInstance().hasSelectedCity()){
            // 还没有选择的城市，直接返回。
            return result;
        }
        for (CityInfo cityInfo : result) {
            // 对比 cityId 和 location，设置selected 字段。
            if(SelectedCitiesManager.getInstance().isCitySelected(cityInfo)){
                cityInfo.setSelected(true);
            }else{
                cityInfo.setSelected(false);
            }
        }
        return result;
    }

    private List<CityInfo> convertCityListJSON2CityInfoList(CityListJSON cityListJSON){
        return cityListJSON.getResult().get(0).getCityInfoList();
    }

    /**
     * 从给定字符串中筛选中文
     * @param text 文本
     * @return 筛选出的中文文本
     */
    private static String remainChinese(String text) {
        StringBuilder builder = new StringBuilder();
        if(!TextUtils.isEmpty(text)){
            Matcher matcher = sPattern.matcher(text);
            while (matcher.find()){
                builder.append(matcher.group());
            }
        }
        return builder.toString();
    }
}
