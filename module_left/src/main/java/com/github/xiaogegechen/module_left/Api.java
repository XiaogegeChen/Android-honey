package com.github.xiaogegechen.module_left;

import com.github.xiaogegechen.module_left.model.json.CityListJSON;
import com.github.xiaogegechen.module_left.model.json.TopCityJSON;
import com.github.xiaogegechen.module_left.model.json.WeatherHourlyJSON;
import com.github.xiaogegechen.module_left.model.json.WeatherNowJSON;

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
    Call<WeatherNowJSON> queryWeatherNow(@Query("key") String key,
                                         @Query("location") String location);

    /**
     * 逐小时预报
     * @param key 和风天气申请的key
     * @param location 城市代码
     * @return 逐小时预报的天气状态
     */
    @GET("hourly")
    Call<WeatherHourlyJSON> queryWeatherHourly(@Query("key") String key,
                                               @Query("location") String location);

}
