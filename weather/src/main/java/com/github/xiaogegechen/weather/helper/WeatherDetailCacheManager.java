package com.github.xiaogegechen.weather.helper;

import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.WeatherApplication;
import com.github.xiaogegechen.weather.model.db.WeatherDetailCache;
import com.github.xiaogegechen.weather.model.db.WeatherDetailCacheDao;

import java.util.List;

/**
 * 管理缓存的天气信息的类，单例模式
 */
public class WeatherDetailCacheManager {
    public static final int REFRESH_TIME_NONE = 0;

    private static volatile WeatherDetailCacheManager sInstance;

    public static WeatherDetailCacheManager getInstance(){
        if (sInstance == null) {
            synchronized (WeatherDetailCacheManager.class){
                if (sInstance == null) {
                    sInstance = new WeatherDetailCacheManager();
                }
            }
        }
        return sInstance;
    }

    private WeatherDetailCacheDao mWeatherDetailCacheDao;

    private WeatherDetailCacheManager(){
        mWeatherDetailCacheDao = WeatherApplication.getDaoSession().getWeatherDetailCacheDao();
    }

    /**
     * 获取一个城市上次更新的时间，如果表中没有这个城市的记录，返回{@link #REFRESH_TIME_NONE}
     *
     * @param cityId 指定城市的id
     * @return 上次记录的更新时间
     */
    public long getLastRefreshTime(String cityId){
        List<WeatherDetailCache> list = mWeatherDetailCacheDao.queryBuilder()
                .where(WeatherDetailCacheDao.Properties.CityId.eq(cityId))
                .build()
                .list();
        if(list == null || list.size() == 0){
            return REFRESH_TIME_NONE;
        }
        return list.get(0).getRefreshTime();
    }

    /**
     * 判断一个城市是否需要更新天气信息，如果距离上次更新超过阈值，返回true，表示需要更新
     *
     * @param cityId 指定城市id
     * @return 如果需要更新返回true，否则返回false
     */
    public boolean shouldRefresh(String cityId){
        return System.currentTimeMillis() - getLastRefreshTime(cityId) > Constants.WEATHER_REFRESH_INTERVAL;
    }

    /**
     * 缓存实时天气，如果数据之前有这个城市的记录则更改，没有则添加一条该城市的记录。
     * 这个方法会更改刷新时间
     * @param cityId 城市
     * @param nowJsonData 实时天气，json格式的数据
     */
    public void saveNowData(String cityId, String nowJsonData){
        List<WeatherDetailCache> list = mWeatherDetailCacheDao.queryBuilder()
                .where(WeatherDetailCacheDao.Properties.CityId.eq(cityId))
                .build()
                .list();
        if(list == null || list.size() == 0){
            WeatherDetailCache weatherDetailCache = new WeatherDetailCache();
            weatherDetailCache.setCityId(cityId);
            weatherDetailCache.setNow(nowJsonData);
            weatherDetailCache.setRefreshTime(System.currentTimeMillis());
            mWeatherDetailCacheDao.insert(weatherDetailCache);
        }else{
            WeatherDetailCache weatherDetailCache = list.get(0);
            weatherDetailCache.setNow(nowJsonData);
            weatherDetailCache.setRefreshTime(System.currentTimeMillis());
            mWeatherDetailCacheDao.update(weatherDetailCache);
        }
    }

    public String getNowData(String cityId){
        List<WeatherDetailCache> list = mWeatherDetailCacheDao.queryBuilder()
                .where(WeatherDetailCacheDao.Properties.CityId.eq(cityId))
                .build()
                .list();
        if(list == null || list.size() == 0){
            return null;
        }else{
            return list.get(0).getNow();
        }
    }

    public void saveHourlyData(String cityId, String hourlyJsonData){
        List<WeatherDetailCache> list = mWeatherDetailCacheDao.queryBuilder()
                .where(WeatherDetailCacheDao.Properties.CityId.eq(cityId))
                .build()
                .list();
        if(list == null || list.size() == 0){
            WeatherDetailCache weatherDetailCache = new WeatherDetailCache();
            weatherDetailCache.setCityId(cityId);
            weatherDetailCache.setHourly(hourlyJsonData);
            weatherDetailCache.setRefreshTime(System.currentTimeMillis());
            mWeatherDetailCacheDao.insert(weatherDetailCache);
        }else{
            WeatherDetailCache weatherDetailCache = list.get(0);
            weatherDetailCache.setHourly(hourlyJsonData);
            weatherDetailCache.setRefreshTime(System.currentTimeMillis());
            mWeatherDetailCacheDao.update(weatherDetailCache);
        }
    }

    public String getHourlyData(String cityId){
        List<WeatherDetailCache> list = mWeatherDetailCacheDao.queryBuilder()
                .where(WeatherDetailCacheDao.Properties.CityId.eq(cityId))
                .build()
                .list();
        if(list == null || list.size() == 0){
            return null;
        }else{
            return list.get(0).getHourly();
        }
    }

    public void saveForecastData(String cityId, String forecastJsonData){
        List<WeatherDetailCache> list = mWeatherDetailCacheDao.queryBuilder()
                .where(WeatherDetailCacheDao.Properties.CityId.eq(cityId))
                .build()
                .list();
        if(list == null || list.size() == 0){
            WeatherDetailCache weatherDetailCache = new WeatherDetailCache();
            weatherDetailCache.setCityId(cityId);
            weatherDetailCache.setForecast(forecastJsonData);
            weatherDetailCache.setRefreshTime(System.currentTimeMillis());
            mWeatherDetailCacheDao.insert(weatherDetailCache);
        }else{
            WeatherDetailCache weatherDetailCache = list.get(0);
            weatherDetailCache.setForecast(forecastJsonData);
            weatherDetailCache.setRefreshTime(System.currentTimeMillis());
            mWeatherDetailCacheDao.update(weatherDetailCache);
        }
    }

    public String getForecastData(String cityId){
        List<WeatherDetailCache> list = mWeatherDetailCacheDao.queryBuilder()
                .where(WeatherDetailCacheDao.Properties.CityId.eq(cityId))
                .build()
                .list();
        if(list == null || list.size() == 0){
            return null;
        }else{
            return list.get(0).getForecast();
        }
    }

    public void saveLifestyleData(String cityId, String lifestyleJsonData){
        List<WeatherDetailCache> list = mWeatherDetailCacheDao.queryBuilder()
                .where(WeatherDetailCacheDao.Properties.CityId.eq(cityId))
                .build()
                .list();
        if(list == null || list.size() == 0){
            WeatherDetailCache weatherDetailCache = new WeatherDetailCache();
            weatherDetailCache.setCityId(cityId);
            weatherDetailCache.setLifestyle(lifestyleJsonData);
            weatherDetailCache.setRefreshTime(System.currentTimeMillis());
            mWeatherDetailCacheDao.insert(weatherDetailCache);
        }else{
            WeatherDetailCache weatherDetailCache = list.get(0);
            weatherDetailCache.setLifestyle(lifestyleJsonData);
            weatherDetailCache.setRefreshTime(System.currentTimeMillis());
            mWeatherDetailCacheDao.update(weatherDetailCache);
        }
    }

    public String getLifestyleData(String cityId){
        List<WeatherDetailCache> list = mWeatherDetailCacheDao.queryBuilder()
                .where(WeatherDetailCacheDao.Properties.CityId.eq(cityId))
                .build()
                .list();
        if(list == null || list.size() == 0){
            return null;
        }else{
            return list.get(0).getLifestyle();
        }
    }
}
