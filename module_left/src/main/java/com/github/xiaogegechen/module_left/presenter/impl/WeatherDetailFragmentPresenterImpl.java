package com.github.xiaogegechen.module_left.presenter.impl;

import android.app.Activity;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.module_left.Api;
import com.github.xiaogegechen.module_left.Constants;
import com.github.xiaogegechen.module_left.R;
import com.github.xiaogegechen.module_left.model.CityInfo;
import com.github.xiaogegechen.module_left.model.WeatherAir;
import com.github.xiaogegechen.module_left.model.json.WeatherHourlyJSON;
import com.github.xiaogegechen.module_left.model.json.WeatherNowJSON;
import com.github.xiaogegechen.module_left.presenter.IWeatherDetailFragmentPresenter;
import com.github.xiaogegechen.module_left.view.IWeatherDetailFragmentView;
import com.github.xiaogegechen.module_left.view.impl.WeatherDetailFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDetailFragmentPresenterImpl implements IWeatherDetailFragmentPresenter {

    private static final String TEMP_SUFFIX = "°C";
    private static final String DEGREE_SUFFIX = "°";
    private static final String SPEED_SUFFIX = "km/h";
    private static final String HUMIDITY_SUFFIX = "km/h";
    private static final String PRECIPITATION_SUFFIX = "mm";
    private static final String PRESSURE_SUFFIX = "hPa";
    private static final String VISIBILITY_SUFFIX = "km";

    private IWeatherDetailFragmentView mWeatherDetailFragmentView;
    private Activity mActivity;

    private Retrofit mHWeatherRetrofit;
    private Call<WeatherNowJSON> mWeatherNowCall;
    private Call<WeatherHourlyJSON> mWeatherHourlyCall;

    @Override
    public void attach(IWeatherDetailFragmentView weatherDetailFragmentView) {
        mWeatherDetailFragmentView = weatherDetailFragmentView;
        mActivity = ((WeatherDetailFragment) weatherDetailFragmentView).obtainActivity();
        mHWeatherRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEATHER_QUERY_BASIC_WEATHER_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void detach() {
        RetrofitHelper.cancelCalls(mWeatherNowCall, mWeatherHourlyCall);
        mWeatherDetailFragmentView = null;
    }

    @Override
    public void queryWeather(CityInfo cityInfo) {
        queryNow(cityInfo);
        queryHourly(cityInfo);
    }

    /**
     * 请求实时天气并显示
     * @param cityInfo 指定城市
     */
    private void queryNow(CityInfo cityInfo){
        mWeatherNowCall = mHWeatherRetrofit.create(Api.class).queryWeatherNow(Constants.WEATHER_KEY, cityInfo.getCityId());
        mWeatherNowCall.enqueue(new Callback<WeatherNowJSON>() {
            @Override
            public void onResponse(@NotNull Call<WeatherNowJSON> call, @NotNull Response<WeatherNowJSON> response) {
                WeatherNowJSON body = response.body();
                if(isWeatherNowJSONOK(body)){
                    WeatherNowJSON.Result.Now now = body.getResult().get(0).getNow();
                    String tempText = now.getTmp() + TEMP_SUFFIX;
                    String weatherDescriptionText = now.getCondDescription();
                    String compareText = compareTempWithYesterday(tempText);
                    List<WeatherAir> weatherAirList = convertWeatherNowJSON2WeatherAirList(body);
                    mWeatherDetailFragmentView.showNow(tempText, weatherDescriptionText, compareText, weatherAirList);
                }else{
                    mWeatherDetailFragmentView.showToast(Constants.NOW_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<WeatherNowJSON> call, @NotNull Throwable t) {
                if(!call.isCanceled()){
                    mWeatherDetailFragmentView.showToast(Constants.NOW_ERROR_MESSAGE);
                    // TODO 使用缓存
                }
            }
        });
    }

    /**
     * 请求24小时天气并显示
     * @param cityInfo 指定城市
     */
    private void queryHourly(CityInfo cityInfo){
        mWeatherHourlyCall = mHWeatherRetrofit.create(Api.class).queryWeatherHourly(Constants.WEATHER_KEY, cityInfo.getCityId());
        mWeatherHourlyCall.enqueue(new Callback<WeatherHourlyJSON>() {
            @Override
            public void onResponse(@NotNull Call<WeatherHourlyJSON> call, @NotNull Response<WeatherHourlyJSON> response) {
                WeatherHourlyJSON body = response.body();
                if(isWeatherHourlyJSONOK(body)){

                }else{
                    mWeatherDetailFragmentView.showToast(Constants.HOURLY_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<WeatherHourlyJSON> call, @NotNull Throwable t) {
                if(!call.isCanceled()){
                    mWeatherDetailFragmentView.showToast(Constants.HOURLY_ERROR_MESSAGE);
                    // TODO 使用缓存
                }
            }
        });
    }

    private String compareTempWithYesterday(String tempNow){
        // TODO 拿到昨天温度
        int tempYesterday = 0;
        int diff = 0;
        try{
            diff = Integer.parseInt(tempNow) - tempYesterday;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(diff >= 0){
            return "高" + diff + TEMP_SUFFIX;
        }else{
            diff = 0 - diff;
            return "低" + diff + TEMP_SUFFIX;
        }
    }

    /**
     * 把从网络拿到的数据转化为空气质量
     * @param weatherNowJSON 原始数据
     * @return 空气质量
     */
    private List<WeatherAir> convertWeatherNowJSON2WeatherAirList(WeatherNowJSON weatherNowJSON){
        List<WeatherAir> result = new ArrayList<>();
        WeatherNowJSON.Result.Now now = weatherNowJSON.getResult().get(0).getNow();
        // 体感温度
        WeatherAir weatherAirFeelTemp = new WeatherAir("体感温度", now.getFeelTemp() + TEMP_SUFFIX);
        weatherAirFeelTemp.setUIColorId(mActivity.getResources().getColor(R.color.module_left_weather_color_feel_temp));
        result.add(weatherAirFeelTemp);
        // 风向角
        WeatherAir weatherAirWindDegree = new WeatherAir("风向角", now.getWindDegree() + DEGREE_SUFFIX);
        weatherAirWindDegree.setUIColorId(mActivity.getResources().getColor(R.color.module_left_weather_color_wind_degree));
        result.add(weatherAirWindDegree);
        // 风向
        WeatherAir weatherAirWindDirection = new WeatherAir("风向", now.getWindDirection());
        weatherAirWindDirection.setUIColorId(mActivity.getResources().getColor(R.color.module_left_weather_color_wind_direction));
        result.add(weatherAirWindDirection);
        // 风力
        WeatherAir weatherAirWindPower = new WeatherAir("风力", now.getWindPower());
        weatherAirWindPower.setUIColorId(mActivity.getResources().getColor(R.color.module_left_weather_color_wind_power));
        result.add(weatherAirWindPower);
        // 风速
        WeatherAir weatherAirWindSpeed = new WeatherAir("风速", now.getWindSpeed() + SPEED_SUFFIX);
        weatherAirWindSpeed.setUIColorId(mActivity.getResources().getColor(R.color.module_left_weather_color_wind_power));
        result.add(weatherAirWindSpeed);
        // 相对湿度
        WeatherAir weatherAirHumidity = new WeatherAir("湿度", now.getHumidity() + HUMIDITY_SUFFIX);
        weatherAirHumidity.setUIColorId(mActivity.getResources().getColor(R.color.module_left_weather_color_humidity));
        result.add(weatherAirHumidity);
        // 降水量
        WeatherAir weatherAirPrecipitation = new WeatherAir("降水量", now.getPrecipitation() + PRECIPITATION_SUFFIX);
        weatherAirPrecipitation.setUIColorId(mActivity.getResources().getColor(R.color.module_left_weather_color_precipitation));
        result.add(weatherAirPrecipitation);
        // 大气压强
        WeatherAir weatherAirPressure = new WeatherAir("大气压强", now.getPressure() + PRESSURE_SUFFIX);
        weatherAirPressure.setUIColorId(mActivity.getResources().getColor(R.color.module_left_weather_color_pressure));
        result.add(weatherAirPressure);
        // 能见度
        WeatherAir weatherAirVisibility = new WeatherAir("能见度", now.getVisibility() + VISIBILITY_SUFFIX);
        weatherAirVisibility.setUIColorId(mActivity.getResources().getColor(R.color.module_left_weather_color_visibility));
        result.add(weatherAirVisibility);
        // 云量
        WeatherAir weatherAirCloud = new WeatherAir("云量", now.getCloud());
        weatherAirCloud.setUIColorId(mActivity.getResources().getColor(R.color.module_left_weather_color_cloud));
        result.add(weatherAirCloud);
        return result;
    }

    // 响应成功
    private static boolean isWeatherNowJSONOK(WeatherNowJSON weatherNowJSON){
        return weatherNowJSON != null && "ok" .equals(weatherNowJSON.getResult().get(0).getStatus());
    }

        private static boolean isWeatherHourlyJSONOK(WeatherHourlyJSON weatherHourlyJSON){
        return weatherHourlyJSON != null && "ok" .equals(weatherHourlyJSON.getResult().get(0).getStatus());
    }
}
