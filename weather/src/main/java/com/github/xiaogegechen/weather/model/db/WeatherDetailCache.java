package com.github.xiaogegechen.weather.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 缓存城市详细天气信息的表，这个表中每个城市都只有一行，记录了上一次这个城市详细的天气信息。这在没有网络或者短时间内切换
 * viewPager时会很有用。不直接操作数据库，通过{@link com.github.xiaogegechen.weather.helper.WeatherDetailCacheManager}
 * 中的API操作。
 */
@Entity(indexes = {
        @Index(value = "cityId", unique = true)
})
public class WeatherDetailCache {
    @Id
    private Long id;

    // 地区／城市ID
    private String cityId;

    // 地区／城市名称
    private String location;

    // 更新时间
    private long refreshTime;

    // 现在天气状况，json格式的数据
    private String now;

    // 小时天气数据，json格式的数据
    private String hourly;

    // 未来七天预报天气数据，json格式的数据
    private String forecast;

    // 生活建议天气数据，json格式的数据
    private String lifestyle;

    public WeatherDetailCache(){}

    @Generated(hash = 140834747)
    public WeatherDetailCache(Long id, String cityId, String location,
            long refreshTime, String now, String hourly, String forecast,
            String lifestyle) {
        this.id = id;
        this.cityId = cityId;
        this.location = location;
        this.refreshTime = refreshTime;
        this.now = now;
        this.hourly = hourly;
        this.forecast = forecast;
        this.lifestyle = lifestyle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getHourly() {
        return hourly;
    }

    public void setHourly(String hourly) {
        this.hourly = hourly;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
    }
}
