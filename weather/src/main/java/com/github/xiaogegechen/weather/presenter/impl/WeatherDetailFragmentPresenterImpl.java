package com.github.xiaogegechen.weather.presenter.impl;

import androidx.annotation.IntDef;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.network.CheckHelper;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.common.util.TimeUtils;
import com.github.xiaogegechen.weather.Api;
import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.helper.WeatherDetailCacheManager;
import com.github.xiaogegechen.weather.model.Air;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.Forecast;
import com.github.xiaogegechen.weather.model.Hourly;
import com.github.xiaogegechen.weather.model.Lifestyle;
import com.github.xiaogegechen.weather.model.json.ForecastJSON;
import com.github.xiaogegechen.weather.model.json.HourlyJSON;
import com.github.xiaogegechen.weather.model.json.LifestyleJSON;
import com.github.xiaogegechen.weather.model.json.NowJSON;
import com.github.xiaogegechen.weather.presenter.IWeatherDetailFragmentPresenter;
import com.github.xiaogegechen.weather.view.IWeatherDetailFragmentView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDetailFragmentPresenterImpl implements IWeatherDetailFragmentPresenter {
    private static final String TAG = "WeatherDetailFragmentPr";
    private static final String TEMP_SUFFIX = "°C";
    private static final String DEGREE_SUFFIX = "°";
    private static final String SPEED_SUFFIX = "km/h";
    private static final String HUMIDITY_SUFFIX = "km/h";
    private static final String PRECIPITATION_SUFFIX = "mm";
    private static final String PRESSURE_SUFFIX = "hPa";
    private static final String VISIBILITY_SUFFIX = "km";

    private static final int FROM_DB = 100;
    private static final int FROM_NETWORK = 101;
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            FROM_DB,
            FROM_NETWORK
    })
    private @interface DataFrom{}

    private IWeatherDetailFragmentView mWeatherDetailFragmentView;

    private Retrofit mHWeatherRetrofit;
    private Call<NowJSON> mWeatherNowCall;
    private Call<HourlyJSON> mWeatherHourlyCall;
    private Call<ForecastJSON> mWeatherForecastCall;
    private Call<LifestyleJSON> mWeatherLiftStyleCall;

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
        if(WeatherDetailCacheManager.getInstance().shouldRefresh(cityInfo.getCityId())){
            queryWeatherFromNetwork(cityInfo);
        }else{
            queryWeatherFromDb(cityInfo);
        }
    }

    /**
     * 从网络拉取天气信息
     * @param cityInfo 指定的城市
     */
    @Override
    public void queryWeatherFromNetwork(CityInfo cityInfo) {
        logInfo("query weather from network, cityId -> " + cityInfo.getCityId() + ", location -> " + cityInfo.getLocation());
        queryNowFromNetWork(cityInfo);
        queryHourlyFromNetWork(cityInfo);
        queryForecastFromNetWork(cityInfo);
        queryLifestyleFromNetWork(cityInfo);
    }

    /**
     * 从数据库拉取天气信息
     * @param cityInfo 指定城市
     */
    private void queryWeatherFromDb(CityInfo cityInfo) {
        logInfo("query weather from db, cityId -> " + cityInfo.getCityId() + ", location -> " + cityInfo.getLocation());
        queryNowFromDb(cityInfo);
        queryHourlyFromDb(cityInfo);
        queryForecastFromDb(cityInfo);
        queryLifestyleFromDb(cityInfo);
    }

    /**
     * 请求实时天气并显示
     * @param cityInfo 指定城市
     */
    private void queryNowFromNetWork(final CityInfo cityInfo){
        // 先取消掉之前的请求，再做新的请求
        RetrofitHelper.cancelCall(mWeatherNowCall);
        mWeatherDetailFragmentView.showSwipeRefresh();
        mWeatherNowCall = mHWeatherRetrofit.create(Api.class).queryWeatherNow(Constants.WEATHER_KEY, cityInfo.getCityId());
        mWeatherNowCall.enqueue(new Callback<NowJSON>() {
            @Override
            public void onResponse(@NotNull Call<NowJSON> call, @NotNull Response<NowJSON> response) {
                logInfo("query now from network success, cityId -> " + cityInfo.getCityId());
                mWeatherDetailFragmentView.hideSwipeRefresh();
                final NowJSON body = response.body();
                handleNowJSON(cityInfo, body, FROM_NETWORK);
            }

            @Override
            public void onFailure(@NotNull Call<NowJSON> call, @NotNull Throwable t) {
                logError("query now from network failed, cityId -> " + cityInfo.getCityId() + ", exception -> " + t.getMessage());
                t.printStackTrace();
                if(!call.isCanceled()){
                    mWeatherDetailFragmentView.hideSwipeRefresh();
                    mWeatherDetailFragmentView.showToast(Constants.NOW_ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * 请求24小时天气并显示
     * @param cityInfo 指定城市
     */
    private void queryHourlyFromNetWork(CityInfo cityInfo){
        // 先取消掉之前的请求，再做新的请求
        RetrofitHelper.cancelCall(mWeatherHourlyCall);
        mWeatherDetailFragmentView.showSwipeRefresh();
        mWeatherHourlyCall = mHWeatherRetrofit.create(Api.class).queryWeatherHourly(Constants.WEATHER_KEY, cityInfo.getCityId());
        mWeatherHourlyCall.enqueue(new Callback<HourlyJSON>() {
            @Override
            public void onResponse(@NotNull Call<HourlyJSON> call, @NotNull Response<HourlyJSON> response) {
                logInfo("query hourly from network success, cityId -> " + cityInfo.getCityId());
                mWeatherDetailFragmentView.hideSwipeRefresh();
                final HourlyJSON body = response.body();
                handleHourlyJSON(cityInfo, body, FROM_NETWORK);
            }

            @Override
            public void onFailure(@NotNull Call<HourlyJSON> call, @NotNull Throwable t) {
                logError("query hourly from network failed, cityId -> " + cityInfo.getCityId() + ", exception -> " + t.getMessage());
                t.printStackTrace();
                if(!call.isCanceled()){
                    mWeatherDetailFragmentView.hideSwipeRefresh();
                    mWeatherDetailFragmentView.showToast(Constants.HOURLY_ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * 请求未来几天天气并显示
     * @param cityInfo 指定城市
     */
    private void queryForecastFromNetWork(CityInfo cityInfo){
        // 先取消掉之前的请求，再做新的请求
        RetrofitHelper.cancelCall(mWeatherForecastCall);
        mWeatherDetailFragmentView.showSwipeRefresh();
        mWeatherForecastCall = mHWeatherRetrofit.create(Api.class).queryWeatherForecast(Constants.WEATHER_KEY, cityInfo.getCityId());
        mWeatherForecastCall.enqueue(new Callback<ForecastJSON>() {
            @Override
            public void onResponse(@NotNull Call<ForecastJSON> call, @NotNull Response<ForecastJSON> response) {
                logInfo("query forecast from network success, cityId -> " + cityInfo.getCityId());
                mWeatherDetailFragmentView.hideSwipeRefresh();
                final ForecastJSON body = response.body();
                handleForecastJSON(cityInfo, body, FROM_NETWORK);
            }

            @Override
            public void onFailure(@NotNull Call<ForecastJSON> call, @NotNull Throwable t) {
                logError("query forecast from network failed, cityId -> " + cityInfo.getCityId() + ", exception -> " + t.getMessage());
                t.printStackTrace();
                if(!call.isCanceled()){
                    mWeatherDetailFragmentView.hideSwipeRefresh();
                    mWeatherDetailFragmentView.showToast(Constants.FORECAST_ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * 请求生活建议并显示
     * @param cityInfo 指定城市
     */
    private void queryLifestyleFromNetWork(CityInfo cityInfo){
        // 先取消掉之前的请求，再做新的请求
        RetrofitHelper.cancelCall(mWeatherLiftStyleCall);
        mWeatherDetailFragmentView.showSwipeRefresh();
        mWeatherLiftStyleCall = mHWeatherRetrofit.create(Api.class).queryWeatherLifestyle(Constants.WEATHER_KEY, cityInfo.getCityId());
        mWeatherLiftStyleCall.enqueue(new Callback<LifestyleJSON>() {
            @Override
            public void onResponse(@NotNull Call<LifestyleJSON> call, @NotNull Response<LifestyleJSON> response) {
                logInfo("query lifestyle from network success, cityId -> " + cityInfo.getCityId());
                mWeatherDetailFragmentView.hideSwipeRefresh();
                final LifestyleJSON body = response.body();
                handleLifestyleJSON(cityInfo, body, FROM_NETWORK);
            }

            @Override
            public void onFailure(@NotNull Call<LifestyleJSON> call, @NotNull Throwable t) {
                logError("query lifestyle from network failed, cityId -> " + cityInfo.getCityId() + ", exception -> " + t.getMessage());
                t.printStackTrace();
                if(!call.isCanceled()){
                    mWeatherDetailFragmentView.hideSwipeRefresh();
                    mWeatherDetailFragmentView.showToast(Constants.LIFESTYLE_ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * 从数据库获取实时天气并显示
     * @param cityInfo 指定城市
     */
    private void queryNowFromDb(final CityInfo cityInfo){
        String nowDataJson = WeatherDetailCacheManager.getInstance().getNowData(cityInfo.getCityId());
        if (nowDataJson != null) {
            Gson gson = new Gson();
            try {
                NowJSON nowJSON = gson.fromJson(nowDataJson, NowJSON.class);
                handleNowJSON(cityInfo, nowJSON, FROM_DB);
            }catch (JsonSyntaxException e){
                e.printStackTrace();
                logError("query now from db, cityId -> " + cityInfo.getCityId() + ", met json syntax error -> " + e.getMessage() + ", so query from net!");
                queryNowFromNetWork(cityInfo);
            }
        }else{
            logInfo("query now from db, json is null, cityId -> " + cityInfo.getCityId() + ", so query from net!");
            queryNowFromNetWork(cityInfo);
        }
    }

    /**
     * 从数据库获取每小时天气并显示
     * @param cityInfo 指定城市
     */
    private void queryHourlyFromDb(final CityInfo cityInfo){
        String hourlyDataJson = WeatherDetailCacheManager.getInstance().getHourlyData(cityInfo.getCityId());
        if (hourlyDataJson != null) {
            Gson gson = new Gson();
            try {
                HourlyJSON hourlyJSON = gson.fromJson(hourlyDataJson, HourlyJSON.class);
                handleHourlyJSON(cityInfo, hourlyJSON, FROM_DB);
            }catch (JsonSyntaxException e){
                e.printStackTrace();
                logError("query hourly from db, cityId -> " + cityInfo.getCityId() + ", met json syntax error -> " + e.getMessage() + ", so query from net!");
                queryHourlyFromNetWork(cityInfo);
            }
        }else{
            logInfo("query hourly from db, json is null, cityId -> " + cityInfo.getCityId() + ", so query from net!");
            queryHourlyFromNetWork(cityInfo);
        }
    }

    /**
     * 从数据库获取未来七天的天气并显示
     * @param cityInfo 指定城市
     */
    private void queryForecastFromDb(final CityInfo cityInfo){
        String forecastDataJson = WeatherDetailCacheManager.getInstance().getForecastData(cityInfo.getCityId());
        if (forecastDataJson != null) {
            Gson gson = new Gson();
            try {
                ForecastJSON forecastJSON = gson.fromJson(forecastDataJson, ForecastJSON.class);
                handleForecastJSON(cityInfo, forecastJSON, FROM_DB);
            }catch (JsonSyntaxException e){
                e.printStackTrace();
                logError("query forecast from db, cityId -> " + cityInfo.getCityId() + ", met json syntax error -> " + e.getMessage() + ", so query from net!");
                queryForecastFromNetWork(cityInfo);
            }
        }else{
            logInfo("query forecast from db, json is null, cityId -> " + cityInfo.getCityId() + ", so query from net!");
            queryForecastFromNetWork(cityInfo);
        }
    }

    /**
     * 从数据库获取生活建议信息并显示
     * @param cityInfo 指定城市
     */
    private void queryLifestyleFromDb(final CityInfo cityInfo){
        String lifestyleDataJson = WeatherDetailCacheManager.getInstance().getLifestyleData(cityInfo.getCityId());
        if (lifestyleDataJson != null) {
            Gson gson = new Gson();
            try {
                LifestyleJSON lifestyleJSON = gson.fromJson(lifestyleDataJson, LifestyleJSON.class);
                handleLifestyleJSON(cityInfo, lifestyleJSON, FROM_DB);
            }catch (JsonSyntaxException e){
                e.printStackTrace();
                logError("query lifestyle from db, cityId -> " + cityInfo.getCityId() + ", met json syntax error -> " + e.getMessage() + ", so query from net!");
                queryLifestyleFromNetWork(cityInfo);
            }
        }else{
            logInfo("query lifestyle from db, json is null, cityId -> " + cityInfo.getCityId() + ", so query from net!");
            queryLifestyleFromNetWork(cityInfo);
        }
    }

    /**
     * 处理查询到的实时天气的结果，可能来自db或者network
     * @param cityInfo 城市
     * @param nowJSON 待处理的json
     * @param from 数据来源，取值为{@link #FROM_DB} or {@link #FROM_NETWORK}
     */
    private void handleNowJSON(final CityInfo cityInfo, final NowJSON nowJSON, final @DataFrom int from){
        if (nowJSON != null) {
            CheckHelper.checkResultFromHWeatherServer(nowJSON, new com.github.xiaogegechen.common.network.Callback() {
                @Override
                public void onSuccess() {
                    logInfo("handle now json which from " + from + " success!");
                    NowJSON.Result.Now now = nowJSON.getResult().get(0).getNow();
                    String tempText = now.getTmp() + TEMP_SUFFIX;
                    String weatherDescriptionText = now.getCondDescription();
                    List<Air> weatherAirList = convertNowJSON2AirList(nowJSON);
                    mWeatherDetailFragmentView.showNow(tempText, weatherDescriptionText, weatherAirList, now.getCondCode());
                    if(from == FROM_NETWORK){
                        // 缓存
                        Gson gson = new Gson();
                        WeatherDetailCacheManager.getInstance().saveNowData(cityInfo.getCityId(), gson.toJson(nowJSON));
                    }
                }

                @Override
                public void onFailure(String errorCode, String errorMessage) {
                    logError("handle now json which from " + from + " failed!" + ", error code -> " + errorCode + "error msg -> " + errorMessage);
                    if(from == FROM_NETWORK){
                        mWeatherDetailFragmentView.showToast(Constants.NOW_ERROR_MESSAGE);
                    }
                }
            });
        }
    }

    /**
     * 处理查询到的逐小时天气的结果，可能来自db或者network
     * @param cityInfo 城市
     * @param hourlyJSON 待处理的json
     * @param from 数据来源，取值为{@link #FROM_DB} or {@link #FROM_NETWORK}
     */
    private void handleHourlyJSON(final CityInfo cityInfo, final HourlyJSON hourlyJSON, final @DataFrom int from){
        if (hourlyJSON != null) {
            CheckHelper.checkResultFromHWeatherServer(hourlyJSON, new com.github.xiaogegechen.common.network.Callback() {
                @Override
                public void onSuccess() {
                    logInfo("handle hourly json which from " + from + " success!");
                    List<Hourly> weatherHourlyList = convertHourlyJSON2HourlyList(hourlyJSON);
                    mWeatherDetailFragmentView.showHourly(weatherHourlyList);
                    if(from == FROM_NETWORK){
                        // 缓存
                        Gson gson = new Gson();
                        WeatherDetailCacheManager.getInstance().saveHourlyData(cityInfo.getCityId(), gson.toJson(hourlyJSON));
                    }
                }

                @Override
                public void onFailure(String errorCode, String errorMessage) {
                    logError("handle hourly json which from " + from + " failed!" + ", error code -> " + errorCode + "error msg -> " + errorMessage);
                    if(from == FROM_NETWORK){
                        mWeatherDetailFragmentView.showToast(Constants.HOURLY_ERROR_MESSAGE);
                    }
                }
            });
        }
    }

    /**
     * 处理查询到的未来七天天气的结果，可能来自db或者network
     * @param cityInfo 城市
     * @param forecastJSON 待处理的json
     * @param from 数据来源，取值为{@link #FROM_DB} or {@link #FROM_NETWORK}
     */
    private void handleForecastJSON(final CityInfo cityInfo, final ForecastJSON forecastJSON, final @DataFrom int from){
        if (forecastJSON != null) {
            CheckHelper.checkResultFromHWeatherServer(forecastJSON, new com.github.xiaogegechen.common.network.Callback() {
                @Override
                public void onSuccess() {
                    logInfo("handle forecast json which from " + from + " success!");
                    List<Forecast> weatherForecastList = convertForecastJSON2ForecastList(forecastJSON);
                    mWeatherDetailFragmentView.showForecast(weatherForecastList);
                    if(from == FROM_NETWORK){
                        // 缓存
                        Gson gson = new Gson();
                        WeatherDetailCacheManager.getInstance().saveForecastData(cityInfo.getCityId(), gson.toJson(forecastJSON));
                    }
                }

                @Override
                public void onFailure(String errorCode, String errorMessage) {
                    logError("handle forecast json which from " + from + " failed!" + ", error code -> " + errorCode + "error msg -> " + errorMessage);
                    if(from == FROM_NETWORK){
                        mWeatherDetailFragmentView.showToast(Constants.FORECAST_ERROR_MESSAGE);
                    }
                }
            });
        }
    }

    /**
     * 处理查询到的生活建议的结果，可能来自db或者network
     * @param cityInfo 城市
     * @param lifestyleJSON 待处理的json
     * @param from 数据来源，取值为{@link #FROM_DB} or {@link #FROM_NETWORK}
     */
    private void handleLifestyleJSON(final CityInfo cityInfo, final LifestyleJSON lifestyleJSON, final @DataFrom int from){
        if (lifestyleJSON != null) {
            CheckHelper.checkResultFromHWeatherServer(lifestyleJSON, new com.github.xiaogegechen.common.network.Callback() {
                @Override
                public void onSuccess() {
                    logInfo("handle lifestyle json which from " + from + " success!");
                    List<Lifestyle> weatherLifestyleList = convertLifestyleJSON2LifestyleList(lifestyleJSON);
                    mWeatherDetailFragmentView.showLifestyle(weatherLifestyleList);
                    if(from == FROM_NETWORK){
                        // 缓存
                        Gson gson = new Gson();
                        WeatherDetailCacheManager.getInstance().saveLifestyleData(cityInfo.getCityId(), gson.toJson(lifestyleJSON));
                    }
                }

                @Override
                public void onFailure(String errorCode, String errorMessage) {
                    logError("handle lifestyle json which from " + from + " failed!" + ", error code -> " + errorCode + "error msg -> " + errorMessage);
                    if(from == FROM_NETWORK){
                        mWeatherDetailFragmentView.showToast(Constants.LIFESTYLE_ERROR_MESSAGE);
                    }
                }
            });
        }
    }

    /**
     * 把从网络拿到的数据转化为空气质量
     *
     * @param weatherNowJSON 原始数据
     * @return 空气质量
     */
    private List<Air> convertNowJSON2AirList(NowJSON weatherNowJSON){
        List<Air> result = new ArrayList<>();
        NowJSON.Result.Now now = weatherNowJSON.getResult().get(0).getNow();
        // 体感温度
        Air weatherAirFeelTemp = new Air("体感温度", now.getFeelTemp() + TEMP_SUFFIX);
        weatherAirFeelTemp.setUIColorId(R.color.weather_color_feel_temp);
        result.add(weatherAirFeelTemp);
        // 风向角
        Air weatherAirWindDegree = new Air("风向角", now.getWindDegree() + DEGREE_SUFFIX);
        weatherAirWindDegree.setUIColorId(R.color.weather_color_wind_degree);
        result.add(weatherAirWindDegree);
        // 风向
        Air weatherAirWindDirection = new Air("风向", now.getWindDirection());
        weatherAirWindDirection.setUIColorId(R.color.weather_color_wind_direction);
        result.add(weatherAirWindDirection);
        // 风力
        Air weatherAirWindPower = new Air("风力", now.getWindPower());
        weatherAirWindPower.setUIColorId(R.color.weather_color_wind_power);
        result.add(weatherAirWindPower);
        // 风速
        Air weatherAirWindSpeed = new Air("风速", now.getWindSpeed() + SPEED_SUFFIX);
        weatherAirWindSpeed.setUIColorId(R.color.weather_color_wind_power);
        result.add(weatherAirWindSpeed);
        // 相对湿度
        Air weatherAirHumidity = new Air("湿度", now.getHumidity() + HUMIDITY_SUFFIX);
        weatherAirHumidity.setUIColorId(R.color.weather_color_humidity);
        result.add(weatherAirHumidity);
        // 降水量
        Air weatherAirPrecipitation = new Air("降水量", now.getPrecipitation() + PRECIPITATION_SUFFIX);
        weatherAirPrecipitation.setUIColorId(R.color.weather_color_precipitation);
        result.add(weatherAirPrecipitation);
        // 大气压强
        Air weatherAirPressure = new Air("大气压强", now.getPressure() + PRESSURE_SUFFIX);
        weatherAirPressure.setUIColorId(R.color.weather_color_pressure);
        result.add(weatherAirPressure);
        // 能见度
        Air weatherAirVisibility = new Air("能见度", now.getVisibility() + VISIBILITY_SUFFIX);
        weatherAirVisibility.setUIColorId(R.color.weather_color_visibility);
        result.add(weatherAirVisibility);
        // 云量
        Air weatherAirCloud = new Air("云量", now.getCloud());
        weatherAirCloud.setUIColorId(R.color.weather_color_cloud);
        result.add(weatherAirCloud);
        return result;
    }

    /**
     * 把从网络拿到的数据转化为逐小时天气
     *
     * @param weatherHourlyJSON 原始数据
     * @return 逐小时天气
     */
    private List<Hourly> convertHourlyJSON2HourlyList(HourlyJSON weatherHourlyJSON){
        List<Hourly> result = new ArrayList<>();
        List<HourlyJSON.Result.Hourly> hourlyListInJSON = weatherHourlyJSON.getResult().get(0).getHourly();
        for (HourlyJSON.Result.Hourly hourlyInJSON : hourlyListInJSON) {
            Hourly weatherHourly = new Hourly();
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
    private List<Forecast> convertForecastJSON2ForecastList(ForecastJSON weatherForecastJSON){
        List<Forecast> result = new ArrayList<>();
        List<ForecastJSON.Result.DailyForecast> dailyForecastListInJSON = weatherForecastJSON.getResult().get(0).getDailyForecastList();
        for (ForecastJSON.Result.DailyForecast dailyForecastInJSON : dailyForecastListInJSON) {
            Forecast weatherForecast = new Forecast();
            // 推演出来的时间，如今天、明天、星期三等
            weatherForecast.setTimeDescription(TimeUtils.getTimeDescriptionFromOriginTime(dailyForecastInJSON.getDate()));
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
    private List<Lifestyle> convertLifestyleJSON2LifestyleList(LifestyleJSON weatherLifestyleJSON){
        List<Lifestyle> result = new ArrayList<>();
        List<LifestyleJSON.Result.Lifestyle> lifestyleListInJSON = weatherLifestyleJSON.getResult().get(0).getLifestyle();
        for (LifestyleJSON.Result.Lifestyle lifestyleInJSON : lifestyleListInJSON) {
            Lifestyle weatherLifestyle = new Lifestyle();
            // 建议类型类型，一共八种
            String type = lifestyleInJSON.getType();
            switch (type){
                case "comf":
                    weatherLifestyle.setName("舒适度指数");
                    weatherLifestyle.setIconId(R.drawable.weather_ic_comf);
                    break;
                case "cw":
                    weatherLifestyle.setName("洗车指数");
                    weatherLifestyle.setIconId(R.drawable.weather_ic_cw);
                    break;
                case "drsg":
                    weatherLifestyle.setName("穿衣指数");
                    weatherLifestyle.setIconId(R.drawable.weather_ic_drsg);
                    break;
                case "flu":
                    weatherLifestyle.setName("感冒指数");
                    weatherLifestyle.setIconId(R.drawable.weather_ic_flu);
                    break;
                case "sport":
                    weatherLifestyle.setName("运动指数");
                    weatherLifestyle.setIconId(R.drawable.weather_ic_sport);
                    break;
                case "trav":
                    weatherLifestyle.setName("旅游指数");
                    weatherLifestyle.setIconId(R.drawable.weather_ic_trav);
                    break;
                case "uv":
                    weatherLifestyle.setName("紫外线指数");
                    weatherLifestyle.setIconId(R.drawable.weather_ic_uv);
                    break;
                case "air":
                    weatherLifestyle.setName("空气指数");
                    weatherLifestyle.setIconId(R.drawable.weather_ic_air);
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

    private static void logError(String msg){
        LogUtil.e(TAG, msg);
    }

    private static void logInfo(String msg){
        LogUtil.i(TAG, msg);
    }
}
