package com.github.xiaogegechen.module_left.presenter.impl;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.module_left.Api;
import com.github.xiaogegechen.module_left.Constants;
import com.github.xiaogegechen.module_left.R;
import com.github.xiaogegechen.module_left.model.CityInfo;
import com.github.xiaogegechen.module_left.model.WeatherAir;
import com.github.xiaogegechen.module_left.model.WeatherForecast;
import com.github.xiaogegechen.module_left.model.WeatherHourly;
import com.github.xiaogegechen.module_left.model.WeatherLifestyle;
import com.github.xiaogegechen.module_left.model.json.WeatherForecastJSON;
import com.github.xiaogegechen.module_left.model.json.WeatherHourlyJSON;
import com.github.xiaogegechen.module_left.model.json.WeatherLifestyleJSON;
import com.github.xiaogegechen.module_left.model.json.WeatherNowJSON;
import com.github.xiaogegechen.module_left.presenter.IWeatherDetailFragmentPresenter;
import com.github.xiaogegechen.module_left.view.IWeatherDetailFragmentView;

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

    private Retrofit mHWeatherRetrofit;
    private Call<WeatherNowJSON> mWeatherNowCall;
    private Call<WeatherHourlyJSON> mWeatherHourlyCall;
    private Call<WeatherForecastJSON> mWeatherForecastCall;
    private Call<WeatherLifestyleJSON> mWeatherLiftStyleCall;

    @Override
    public void attach(IWeatherDetailFragmentView weatherDetailFragmentView) {
        mWeatherDetailFragmentView = weatherDetailFragmentView;
        mHWeatherRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEATHER_QUERY_BASIC_WEATHER_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void detach() {
        RetrofitHelper.cancelCalls(mWeatherNowCall,
                mWeatherHourlyCall,
                mWeatherForecastCall,
                mWeatherLiftStyleCall
        );
        mWeatherDetailFragmentView = null;
    }

    @Override
    public void queryWeather(CityInfo cityInfo) {
        // 先取消掉之前的请求，再做新的请求
        RetrofitHelper.cancelCalls(mWeatherNowCall,
                mWeatherHourlyCall,
                mWeatherForecastCall,
                mWeatherLiftStyleCall
        );
        queryNow(cityInfo);
        queryHourly(cityInfo);
        queryForecast(cityInfo);
        queryLifestyle(cityInfo);
    }

    /**
     * 请求实时天气并显示
     * @param cityInfo 指定城市
     */
    private void queryNow(CityInfo cityInfo){
        mWeatherDetailFragmentView.showSwipeRefresh();
        mWeatherNowCall = mHWeatherRetrofit.create(Api.class).queryWeatherNow(Constants.WEATHER_KEY, cityInfo.getCityId());
        mWeatherNowCall.enqueue(new Callback<WeatherNowJSON>() {
            @Override
            public void onResponse(@NotNull Call<WeatherNowJSON> call, @NotNull Response<WeatherNowJSON> response) {
                mWeatherDetailFragmentView.hideSwipeRefresh();
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
                    mWeatherDetailFragmentView.hideSwipeRefresh();
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
        mWeatherDetailFragmentView.showSwipeRefresh();
        mWeatherHourlyCall = mHWeatherRetrofit.create(Api.class).queryWeatherHourly(Constants.WEATHER_KEY, cityInfo.getCityId());
        mWeatherHourlyCall.enqueue(new Callback<WeatherHourlyJSON>() {
            @Override
            public void onResponse(@NotNull Call<WeatherHourlyJSON> call, @NotNull Response<WeatherHourlyJSON> response) {
                mWeatherDetailFragmentView.hideSwipeRefresh();
                WeatherHourlyJSON body = response.body();
                if(isWeatherHourlyJSONOK(body)){
                    List<WeatherHourly> weatherHourlyList = convertWeatherHourlyJSON2WeatherHourlyList(body);
                    mWeatherDetailFragmentView.showHourly(weatherHourlyList);
                }else{
                    mWeatherDetailFragmentView.showToast(Constants.HOURLY_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<WeatherHourlyJSON> call, @NotNull Throwable t) {
                if(!call.isCanceled()){
                    mWeatherDetailFragmentView.hideSwipeRefresh();
                    mWeatherDetailFragmentView.showToast(Constants.HOURLY_ERROR_MESSAGE);
                    // TODO 使用缓存
                }
            }
        });
    }

    /**
     * 请求未来几天天气并显示
     * @param cityInfo 指定城市
     */
    private void queryForecast(CityInfo cityInfo){
        mWeatherDetailFragmentView.showSwipeRefresh();
        mWeatherForecastCall = mHWeatherRetrofit.create(Api.class).queryWeatherForecast(Constants.WEATHER_KEY, cityInfo.getCityId());
        mWeatherForecastCall.enqueue(new Callback<WeatherForecastJSON>() {
            @Override
            public void onResponse(@NotNull Call<WeatherForecastJSON> call, @NotNull Response<WeatherForecastJSON> response) {
                mWeatherDetailFragmentView.hideSwipeRefresh();
                WeatherForecastJSON body = response.body();
                if(isWeatherForecastJSONOK(body)){
                    List<WeatherForecast> weatherForecastList = convertWeatherForecastJSON2WeatherForecastList(body);
                    mWeatherDetailFragmentView.showForecast(weatherForecastList);
                }else {
                    mWeatherDetailFragmentView.showToast(Constants.FORECAST_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<WeatherForecastJSON> call, @NotNull Throwable t) {
                if(!call.isCanceled()){
                    mWeatherDetailFragmentView.hideSwipeRefresh();
                    mWeatherDetailFragmentView.showToast(Constants.FORECAST_ERROR_MESSAGE);
                    // TODO 使用缓存
                }
            }
        });
    }

    /**
     * 请求生活建议并显示
     * @param cityInfo 指定城市
     */
    private void queryLifestyle(CityInfo cityInfo){
        mWeatherDetailFragmentView.showSwipeRefresh();
        mWeatherLiftStyleCall = mHWeatherRetrofit.create(Api.class).queryWeatherLifestyle(Constants.WEATHER_KEY, cityInfo.getCityId());
        mWeatherLiftStyleCall.enqueue(new Callback<WeatherLifestyleJSON>() {
            @Override
            public void onResponse(@NotNull Call<WeatherLifestyleJSON> call, @NotNull Response<WeatherLifestyleJSON> response) {
                mWeatherDetailFragmentView.hideSwipeRefresh();
                WeatherLifestyleJSON body = response.body();
                if(isWeatherLifestyleJSONOK(body)){
                    List<WeatherLifestyle> weatherLifestyleList = convertWeatherLifestyleJSON2WeatherLifestyleList(body);
                    mWeatherDetailFragmentView.showLifestyle(weatherLifestyleList);
                }else{
                    mWeatherDetailFragmentView.showToast(Constants.LIFESTYLE_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<WeatherLifestyleJSON> call, @NotNull Throwable t) {
                if(!call.isCanceled()){
                    mWeatherDetailFragmentView.hideSwipeRefresh();
                    mWeatherDetailFragmentView.showToast(Constants.LIFESTYLE_ERROR_MESSAGE);
                    // TODO 使用缓存
                }
            }
        });
    }

    /**
     * 和昨天的温度对比，拿到对比结果
     * @param tempNow 当前温度
     * @return 对比结果，如果大于昨天的温度，返回"高_°C"，如果小于昨天的温度，返回"低_°C"，
     */
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
     *
     * @param weatherNowJSON 原始数据
     * @return 空气质量
     */
    private List<WeatherAir> convertWeatherNowJSON2WeatherAirList(WeatherNowJSON weatherNowJSON){
        List<WeatherAir> result = new ArrayList<>();
        WeatherNowJSON.Result.Now now = weatherNowJSON.getResult().get(0).getNow();
        // 体感温度
        WeatherAir weatherAirFeelTemp = new WeatherAir("体感温度", now.getFeelTemp() + TEMP_SUFFIX);
        weatherAirFeelTemp.setUIColorId(R.color.module_left_weather_color_feel_temp);
        result.add(weatherAirFeelTemp);
        // 风向角
        WeatherAir weatherAirWindDegree = new WeatherAir("风向角", now.getWindDegree() + DEGREE_SUFFIX);
        weatherAirWindDegree.setUIColorId(R.color.module_left_weather_color_wind_degree);
        result.add(weatherAirWindDegree);
        // 风向
        WeatherAir weatherAirWindDirection = new WeatherAir("风向", now.getWindDirection());
        weatherAirWindDirection.setUIColorId(R.color.module_left_weather_color_wind_direction);
        result.add(weatherAirWindDirection);
        // 风力
        WeatherAir weatherAirWindPower = new WeatherAir("风力", now.getWindPower());
        weatherAirWindPower.setUIColorId(R.color.module_left_weather_color_wind_power);
        result.add(weatherAirWindPower);
        // 风速
        WeatherAir weatherAirWindSpeed = new WeatherAir("风速", now.getWindSpeed() + SPEED_SUFFIX);
        weatherAirWindSpeed.setUIColorId(R.color.module_left_weather_color_wind_power);
        result.add(weatherAirWindSpeed);
        // 相对湿度
        WeatherAir weatherAirHumidity = new WeatherAir("湿度", now.getHumidity() + HUMIDITY_SUFFIX);
        weatherAirHumidity.setUIColorId(R.color.module_left_weather_color_humidity);
        result.add(weatherAirHumidity);
        // 降水量
        WeatherAir weatherAirPrecipitation = new WeatherAir("降水量", now.getPrecipitation() + PRECIPITATION_SUFFIX);
        weatherAirPrecipitation.setUIColorId(R.color.module_left_weather_color_precipitation);
        result.add(weatherAirPrecipitation);
        // 大气压强
        WeatherAir weatherAirPressure = new WeatherAir("大气压强", now.getPressure() + PRESSURE_SUFFIX);
        weatherAirPressure.setUIColorId(R.color.module_left_weather_color_pressure);
        result.add(weatherAirPressure);
        // 能见度
        WeatherAir weatherAirVisibility = new WeatherAir("能见度", now.getVisibility() + VISIBILITY_SUFFIX);
        weatherAirVisibility.setUIColorId(R.color.module_left_weather_color_visibility);
        result.add(weatherAirVisibility);
        // 云量
        WeatherAir weatherAirCloud = new WeatherAir("云量", now.getCloud());
        weatherAirCloud.setUIColorId(R.color.module_left_weather_color_cloud);
        result.add(weatherAirCloud);
        return result;
    }

    /**
     * 把从网络拿到的数据转化为逐小时天气
     *
     * @param weatherHourlyJSON 原始数据
     * @return 逐小时天气
     */
    private List<WeatherHourly> convertWeatherHourlyJSON2WeatherHourlyList(WeatherHourlyJSON weatherHourlyJSON){
        List<WeatherHourly> result = new ArrayList<>();
        List<WeatherHourlyJSON.Result.Hourly> hourlyListInJSON = weatherHourlyJSON.getResult().get(0).getHourly();
        for (WeatherHourlyJSON.Result.Hourly hourlyInJSON : hourlyListInJSON) {
            WeatherHourly weatherHourly = new WeatherHourly();
            // 温度
            weatherHourly.setTemp(hourlyInJSON.getTmp() + TEMP_SUFFIX);
            // icon
            weatherHourly.setCode(hourlyInJSON.getCondCode());
            // 描述
            weatherHourly.setDescription(hourlyInJSON.getCondDescription());
            // 更新时间
            weatherHourly.setTime(filterHourFromOriginTime(hourlyInJSON.getTime()));
            result.add(weatherHourly);
        }
        return result;
    }

    /**
     * 把从网络拿到的数据转化为未来几天的天气
     *
     * @param weatherForecastJSON 原始数据
     * @return 未来几天的天气
     */
    private List<WeatherForecast> convertWeatherForecastJSON2WeatherForecastList(WeatherForecastJSON weatherForecastJSON){
        List<WeatherForecast> result = new ArrayList<>();
        List<WeatherForecastJSON.Result.DailyForecast> dailyForecastListInJSON = weatherForecastJSON.getResult().get(0).getDailyForecastList();
        for (WeatherForecastJSON.Result.DailyForecast dailyForecastInJSON : dailyForecastListInJSON) {
            WeatherForecast weatherForecast = new WeatherForecast();
            // 推演出来的时间，如今天、明天、星期三等
            weatherForecast.setTimeDescription(getTimeDescriptionFromOriginTime(dailyForecastInJSON.getDate()));
            // 日期时间，如：2019/11/23
            weatherForecast.setTime(formatTime(dailyForecastInJSON.getDate()));
            // 天气状态描述，如：小雨。只使用白天的天气
            weatherForecast.setConditionDescription(dailyForecastInJSON.getCondationDescriptionOfDay());
            // 天气状态码，如：101。只使用白天的天气
            weatherForecast.setCode(dailyForecastInJSON.getCondationCodeOfDay());
            // 最高温度
            weatherForecast.setTempMax(dailyForecastInJSON.getTempMax() + TEMP_SUFFIX);
            // 最低温度
            weatherForecast.setTempMin(dailyForecastInJSON.getTempMin() + TEMP_SUFFIX);
            result.add(weatherForecast);
        }
        return result;
    }

    /**
     * 把从网络拿到的数据转化为生活建议
     *
     * @param weatherLifestyleJSON 原始数据
     * @return 生活建议
     */
    private List<WeatherLifestyle> convertWeatherLifestyleJSON2WeatherLifestyleList(WeatherLifestyleJSON weatherLifestyleJSON){
        List<WeatherLifestyle> result = new ArrayList<>();
        List<WeatherLifestyleJSON.Result.Lifestyle> lifestyleListInJSON = weatherLifestyleJSON.getResult().get(0).getLifestyle();
        for (WeatherLifestyleJSON.Result.Lifestyle lifestyleInJSON : lifestyleListInJSON) {
            WeatherLifestyle weatherLifestyle = new WeatherLifestyle();
            // 建议类型类型，一共八种
            String type = lifestyleInJSON.getType();
            switch (type){
                case "comf":
                    weatherLifestyle.setName("舒适度指数");
                    weatherLifestyle.setIconId(R.drawable.module_left_wether_ic_comf);
                    break;
                case "cw":
                    weatherLifestyle.setName("洗车指数");
                    weatherLifestyle.setIconId(R.drawable.module_left_wether_ic_cw);
                    break;
                case "drsg":
                    weatherLifestyle.setName("穿衣指数");
                    weatherLifestyle.setIconId(R.drawable.module_left_wether_ic_drsg);
                    break;
                case "flu":
                    weatherLifestyle.setName("感冒指数");
                    weatherLifestyle.setIconId(R.drawable.module_left_weather_ic_flu);
                    break;
                case "sport":
                    weatherLifestyle.setName("运动指数");
                    weatherLifestyle.setIconId(R.drawable.module_left_wether_ic_sport);
                    break;
                case "trav":
                    weatherLifestyle.setName("旅游指数");
                    weatherLifestyle.setIconId(R.drawable.module_left_wether_ic_trav);
                    break;
                case "uv":
                    weatherLifestyle.setName("紫外线指数");
                    weatherLifestyle.setIconId(R.drawable.module_left_wether_ic_uv);
                    break;
                case "air":
                    weatherLifestyle.setName("空气指数");
                    weatherLifestyle.setIconId(R.drawable.module_left_wether_ic_air);
                    break;
                default:
                    break;
            }
            // 简短描述
            weatherLifestyle.setValue(lifestyleInJSON.getBriefDescription());
            // 详细描述
            weatherLifestyle.setDetail(lifestyleInJSON.getDescription());
            result.add(weatherLifestyle);
        }
        return result;
    }

    /**
     * 从原始时间中拿到小时部分，原始时间格式为 2019-11-23 19:00 ，拿到的是 19:00部分
     * @param originTime 原始时间 ，如： 2019-11-23 19:00
     * @return 筛选后的时间 ，如：19:00 ，如果遇到异常，将返回 "N/A"
     */
    private static String filterHourFromOriginTime(String originTime){
        String result = Constants.NULL_DATA;
        try{
            result = originTime.split(" ")[1];
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据原始时间推算其相对于今天的时间描述，比如今天是 2019.11.23（周六），那么 "2019-11-23" 将返回"今天"， "2019-11-24"
     * 将返回"明天"，"2019-11-25" 将返回"周一"，以此类推。
     *
     * @param originTime 原始时间， 如：2019-11-25
     * @return 时间相对于今天的描述
     */
    private static String getTimeDescriptionFromOriginTime(String originTime){
        // TODO 计算时间描述
        return "周一";
    }

    /**
     * 格式化时间，比如原始时间是：2019-11-25，格式化后得到的是"2019/11/25"
     * @param originTime 原始时间， 如：2019-11-25
     * @return 格式化之后的时间，如果遇到异常，将返回 "N/A"
     */
    private static String formatTime(String originTime){
        String result = Constants.NULL_DATA;
        try{
            result = originTime.replace("-", "/");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 响应成功
    private static boolean isWeatherNowJSONOK(WeatherNowJSON weatherNowJSON){
        return weatherNowJSON != null && "ok" .equals(weatherNowJSON.getResult().get(0).getStatus());
    }

    private static boolean isWeatherHourlyJSONOK(WeatherHourlyJSON weatherHourlyJSON){
        return weatherHourlyJSON != null && "ok" .equals(weatherHourlyJSON.getResult().get(0).getStatus());
    }

    private static boolean isWeatherForecastJSONOK(WeatherForecastJSON weatherForecastJSON){
        return weatherForecastJSON != null && "ok" .equals(weatherForecastJSON.getResult().get(0).getStatus());
    }

    private static boolean isWeatherLifestyleJSONOK(WeatherLifestyleJSON weatherLifestyleJSON){
        return weatherLifestyleJSON != null && "ok" .equals(weatherLifestyleJSON.getResult().get(0).getStatus());
    }
}
