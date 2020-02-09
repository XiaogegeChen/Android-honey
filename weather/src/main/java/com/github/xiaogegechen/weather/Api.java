package com.github.xiaogegechen.weather;

import com.github.xiaogegechen.weather.model.json.CityListJSON;
import com.github.xiaogegechen.weather.model.json.DayPicJSON;
import com.github.xiaogegechen.weather.model.json.ForecastJSON;
import com.github.xiaogegechen.weather.model.json.HourlyJSON;
import com.github.xiaogegechen.weather.model.json.LifestyleJSON;
import com.github.xiaogegechen.weather.model.json.NowJSON;
import com.github.xiaogegechen.weather.model.json.PrecipitationJSON;
import com.github.xiaogegechen.weather.model.json.SatelliteJSON;
import com.github.xiaogegechen.weather.model.json.SatelliteTypeJSON;
import com.github.xiaogegechen.weather.model.json.TemperatureJSON;
import com.github.xiaogegechen.weather.model.json.TopCityJSON;
import com.github.xiaogegechen.weather.model.json.VisibilityJSON;
import com.github.xiaogegechen.weather.model.json.WindJSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    /**
     * 请求热门城市列表
     * @param key 和风天气申请的key
     * @param group 热门城市的区域限制，取值固定为"cn"，意思是只查询中国的热门城市
     * @return 查询结果
     */
    @GET("top")
    Call<TopCityJSON> queryTopCityList(@Query("key") String key,
                                       @Query("group") String group);

    /**
     * 根据输入请求可能的城市列表
     * @param key 和风天气申请的key
     * @param group 区域限制，取值固定为"cn"，意思是只查询中国的城市
     * @param location 待查询词汇
     * @return 查询结果
     */
    @GET("find")
    Call<CityListJSON> queryCityList(@Query("key") String key,
                                     @Query("group") String group,
                                     @Query("location") String location);

    /**
     * 请求当前时刻的天气状况
     * @param key 和风天气申请的key
     * @param location 城市代码
     * @return 实时天气状况
     */
    @GET("now")
    Call<NowJSON> queryWeatherNow(@Query("key") String key,
                                  @Query("location") String location);

    /**
     * 逐小时预报
     * @param key 和风天气申请的key
     * @param location 城市代码
     * @return 逐小时预报的天气状态
     */
    @GET("hourly")
    Call<HourlyJSON> queryWeatherHourly(@Query("key") String key,
                                        @Query("location") String location);

    /**
     * 未来几天天气
     * @param key 和风天气申请的key
     * @param location 城市代码
     * @return 未来几天天气
     */
    @GET("forecast")
    Call<ForecastJSON> queryWeatherForecast(@Query("key") String key,
                                            @Query("location") String location);

    /**
     * 生活建议
     * @param key 和风天气申请的key
     * @param location 城市代码
     * @return 生活建议
     */
    @GET("lifestyle")
    Call<LifestyleJSON> queryWeatherLifestyle(@Query("key") String key,
                                              @Query("location") String location);

    /**
     * 每日一图
     * @return 每日一图
     */
    @GET("api/bing_pic_day")
    Call<DayPicJSON> queryDayPic();

    /**
     * 降水量分布图
     *
     * @return 降水量分布
     */
    @GET("api/weather/precipitation_real")
    Call<PrecipitationJSON> queryPrecipitation();

    /**
     * 温度分布图
     *
     * @return 温度分布
     */
    @GET("api/weather/temperature_real")
    Call<TemperatureJSON> queryTemperature();

    /**
     * 能见度分布图
     *
     * @return 能见度分布
     */
    @GET("api/weather/visibility_real")
    Call<VisibilityJSON> queryVisibility();

    /**
     * 风向分布图
     *
     * @return 风向分布
     */
    @GET("api/weather/wind_real")
    Call<WindJSON> queryWind();

    /**
     * 卫星云图分类
     *
     * @return 卫星云图分类
     */
    @GET("api/weather/satellite_type_real")
    Call<SatelliteTypeJSON> querySatelliteType();

    /**
     * 卫星云图分布
     *
     * @param type 类型，用数字表示，请求这个接口之前先请求{@link #querySatelliteType()}拿到可选的类型
     * @return 卫星云图
     */
    @GET("api/weather/satellite_real")
    Call<SatelliteJSON> querySatellite(@Query("t_s") String type);
}
