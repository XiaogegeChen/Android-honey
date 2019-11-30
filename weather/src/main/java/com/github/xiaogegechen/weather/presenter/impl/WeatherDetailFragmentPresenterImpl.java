package com.github.xiaogegechen.weather.presenter.impl;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.weather.Api;
import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.R;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        mWeatherNowCall.enqueue(new Callback<NowJSON>() {
            @Override
            public void onResponse(@NotNull Call<NowJSON> call, @NotNull Response<NowJSON> response) {
                mWeatherDetailFragmentView.hideSwipeRefresh();
                NowJSON body = response.body();
                if(isWeatherNowJSONOK(body)){
                    NowJSON.Result.Now now = body.getResult().get(0).getNow();
                    String tempText = now.getTmp() + TEMP_SUFFIX;
                    String weatherDescriptionText = now.getCondDescription();
                    String compareText = compareTempWithYesterday(tempText);
                    List<Air> weatherAirList = convertNowJSON2AirList(body);
                    mWeatherDetailFragmentView.showNow(tempText, weatherDescriptionText, compareText, weatherAirList);
                }else{
                    mWeatherDetailFragmentView.showToast(Constants.NOW_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<NowJSON> call, @NotNull Throwable t) {
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
        mWeatherHourlyCall.enqueue(new Callback<HourlyJSON>() {
            @Override
            public void onResponse(@NotNull Call<HourlyJSON> call, @NotNull Response<HourlyJSON> response) {
                mWeatherDetailFragmentView.hideSwipeRefresh();
                HourlyJSON body = response.body();
                if(isWeatherHourlyJSONOK(body)){
                    List<Hourly> weatherHourlyList = convertHourlyJSON2HourlyList(body);
                    mWeatherDetailFragmentView.showHourly(weatherHourlyList);
                }else{
                    mWeatherDetailFragmentView.showToast(Constants.HOURLY_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<HourlyJSON> call, @NotNull Throwable t) {
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
        mWeatherForecastCall.enqueue(new Callback<ForecastJSON>() {
            @Override
            public void onResponse(@NotNull Call<ForecastJSON> call, @NotNull Response<ForecastJSON> response) {
                mWeatherDetailFragmentView.hideSwipeRefresh();
                ForecastJSON body = response.body();
                if(isWeatherForecastJSONOK(body)){
                    List<Forecast> weatherForecastList = convertForecastJSON2ForecastList(body);
                    mWeatherDetailFragmentView.showForecast(weatherForecastList);
                }else {
                    mWeatherDetailFragmentView.showToast(Constants.FORECAST_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ForecastJSON> call, @NotNull Throwable t) {
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
        mWeatherLiftStyleCall.enqueue(new Callback<LifestyleJSON>() {
            @Override
            public void onResponse(@NotNull Call<LifestyleJSON> call, @NotNull Response<LifestyleJSON> response) {
                mWeatherDetailFragmentView.hideSwipeRefresh();
                LifestyleJSON body = response.body();
                if(isWeatherLifestyleJSONOK(body)){
                    List<Lifestyle> weatherLifestyleList = convertLifestyleJSON2LifestyleList(body);
                    mWeatherDetailFragmentView.showLifestyle(weatherLifestyleList);
                }else{
                    mWeatherDetailFragmentView.showToast(Constants.LIFESTYLE_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<LifestyleJSON> call, @NotNull Throwable t) {
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
     * 根据原始时间推算其相对于今天的时间描述，比如今天是 2019.11.23（周六），那么 "2019-11-23" 将返回"今天"， "2019-11-24"
     * 将返回"明天"，"2019-11-25" 将返回"周一"，以此类推。
     *
     * @param originTime 原始时间， 如：2019-11-25
     * @return 时间相对于今天的描述，计算失败返回 "N/A"
     */
    private static String getTimeDescriptionFromOriginTime(String originTime){
        String result = Constants.NULL_DATA;
        try {
            // 今天
            Calendar today = new GregorianCalendar();
            today.setTime(new Date());
            int todayYear = today.get(Calendar.YEAR);
            int todayMonth = today.get(Calendar.MONTH) + 1;
            int todayDay = today.get(Calendar.DAY_OF_MONTH);
            int todayMonthLength = lengthOfMonth(isLeap(todayYear), todayMonth);

            // 计算明天的日期
            int tomorrowDay = todayDay + 1;
            int tomorrowMonth = todayMonth;
            int tomorrowYear = todayYear;
            if(tomorrowDay > todayMonthLength){
                tomorrowDay = 1;
                tomorrowMonth = todayMonth + 1;
                if(tomorrowMonth > 12){
                    tomorrowMonth = 1;
                    tomorrowYear = todayYear + 1;
                }
            }

            // 从originTime拆分出年月日
            String[] originTimeString = originTime.split("-");
            int originTimeYear = Integer.parseInt(originTimeString[0]);
            int originTimeMonth = Integer.parseInt(originTimeString[1]);
            int originTimeDay = Integer.parseInt(originTimeString[2]);

            if(todayYear == originTimeYear && todayMonth == originTimeMonth && todayDay == originTimeDay){
                return "今天";
            }

            if(tomorrowYear == originTimeYear && tomorrowMonth == originTimeMonth && tomorrowDay == originTimeDay){
                return "明天";
            }

            // 如果超过两天那就计算是周几
            Calendar originTimeCalendar = new GregorianCalendar();
            originTimeCalendar.set(originTimeYear, originTimeMonth - 1, originTimeDay);
            int weekValue = originTimeCalendar.get(Calendar.DAY_OF_WEEK);
            switch (weekValue){
                case Calendar.MONDAY:
                    result = "周一";
                    break;
                case Calendar.TUESDAY:
                    result = "周二";
                    break;
                case Calendar.WEDNESDAY:
                    result = "周三";
                    break;
                case Calendar.THURSDAY:
                    result = "周四";
                    break;
                case Calendar.FRIDAY:
                    result = "周五";
                    break;
                case Calendar.SATURDAY:
                    result = "周六";
                    break;
                case Calendar.SUNDAY:
                    result = "周日";
                    break;
                default:
                    break;

            }

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

    /**
     * 判断一年是不是闰年
     * @param year 年份
     * @return true如果是闰年
     */
    private static boolean isLeap(long year) {
        return ((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0);
    }

    /**
     * 拿到指定月分的天数
     * @param leapYear 是否是闰年
     * @param month 月份
     * @return 这个月的天数
     */
    private static int lengthOfMonth(boolean leapYear, int month) {
        switch (month) {
            case 2:
                return (leapYear ? 29 : 28);
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    // 响应成功
    private static boolean isWeatherNowJSONOK(NowJSON weatherNowJSON){
        return weatherNowJSON != null && "ok" .equals(weatherNowJSON.getResult().get(0).getStatus());
    }

    private static boolean isWeatherHourlyJSONOK(HourlyJSON weatherHourlyJSON){
        return weatherHourlyJSON != null && "ok" .equals(weatherHourlyJSON.getResult().get(0).getStatus());
    }

    private static boolean isWeatherForecastJSONOK(ForecastJSON weatherForecastJSON){
        return weatherForecastJSON != null && "ok" .equals(weatherForecastJSON.getResult().get(0).getStatus());
    }

    private static boolean isWeatherLifestyleJSONOK(LifestyleJSON weatherLifestyleJSON){
        return weatherLifestyleJSON != null && "ok" .equals(weatherLifestyleJSON.getResult().get(0).getStatus());
    }
}
