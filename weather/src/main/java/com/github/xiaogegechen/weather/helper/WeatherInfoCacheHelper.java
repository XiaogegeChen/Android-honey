package com.github.xiaogegechen.weather.helper;

import android.content.Context;

import com.github.xiaogegechen.common.util.XmlIOUtil;
import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.model.CityInfo;

/**
 * 管理天气信息的缓存，单例模式
 *
 * @deprecated 弃用，这个需求不要了
 */
public class WeatherInfoCacheHelper {
    private volatile static WeatherInfoCacheHelper sInstance;

    public static WeatherInfoCacheHelper getInstance(Context applicationContext) {
        if (sInstance == null) {
            synchronized (WeatherInfoCacheHelper.class){
                if (sInstance == null) {
                    sInstance = new WeatherInfoCacheHelper(applicationContext);
                }
            }
        }
        return sInstance;
    }

    private Context mApplicationContext;

    private WeatherInfoCacheHelper(Context applicationContext){
        mApplicationContext = applicationContext;
    }

    /**
     * 更新缓存的上一时刻的温度
     *
     * @param cityInfo 城市信息
     * @param temp 上一时刻的温度
     */
    public void updateLastTemp(CityInfo cityInfo, String temp){
        XmlIOUtil.INSTANCE.write(encodeKey(cityInfo), temp, mApplicationContext);
    }

    /**
     * 拿到指定城市上一时刻的温度，如果获取失败，返回null
     *
     * @param cityInfo 指定的城市
     * @return 上一时刻的温度，如果获取失败，返回null
     */
    public String getLastTemp(CityInfo cityInfo){
        return XmlIOUtil.INSTANCE.read(encodeKey(cityInfo), mApplicationContext);
    }

    /**
     * 拼接sp中的key，形式是 "last_temp_" + cityId
     *
     * @param cityInfo 指定的city
     * @return sp中这个city的key
     */
    private static String encodeKey(CityInfo cityInfo){
        String cityId = cityInfo.getCityId();
        return Constants.XML_KEY_LAST_TEMP_PREFIX + cityId;
    }
}
