package com.github.xiaogegechen.weather.helper;

import com.github.xiaogegechen.weather.WeatherApplication;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.db.SelectedCity;
import com.github.xiaogegechen.weather.model.db.SelectedCityDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * 已选城市的管理类，单例
 */
public class SelectedCitiesManager {

    private static volatile SelectedCitiesManager sInstance;

    public static SelectedCitiesManager getInstance() {
        if (sInstance == null) {
            synchronized (SelectedCitiesManager.class){
                if (sInstance == null) {
                    sInstance = new SelectedCitiesManager();
                }
            }
        }
        return sInstance;
    }

    private SelectedCityDao mSelectedCityDao;

    private SelectedCitiesManager(){
        mSelectedCityDao = WeatherApplication.getDaoSession().getSelectedCityDao();
    }

    /**
     * 查询所有已经选择的城市
     *
     * @return 所有已经选择的城市的列表
     */
    public List<SelectedCity> getSelectedCities(){
        Query<SelectedCity> queryAll = mSelectedCityDao.queryBuilder()
                .build();
        return queryAll.list();
    }

    /**
     * 是否存在已经选择的城市，如果存在返回true,否则返回false
     *
     * @return 如果存在返回true,否则返回false
     */
    public boolean hasSelectedCity(){
        Query<SelectedCity> queryAll = mSelectedCityDao.queryBuilder()
                .build();
        List<SelectedCity> list = queryAll.list();
        return list != null && list.size() > 0;
    }

    /**
     * 指定城市是否在数据库中，如果存在返回true,否则返回false
     *
     * @param cityId 城市代码
     * @param location 城市名称
     * @return 如果存在返回true,否则返回false
     */
    public boolean isCitySelected(String cityId, String location){
        Query<SelectedCity> query = mSelectedCityDao.queryBuilder()
                .where(SelectedCityDao.Properties.CityId.eq(cityId), SelectedCityDao.Properties.Location.eq(location))
                .build();
        List<SelectedCity> list = query.list();
        return list != null && list.size() > 0;
    }

    /**
     * 指定城市是否在数据库中，如果存在返回true,否则返回false
     *
     * @param cityInfo 指定城市
     * @return 如果存在返回true,否则返回false
     */
    public boolean isCitySelected(CityInfo cityInfo){
        return isCitySelected(cityInfo.getCityId(), cityInfo.getLocation());
    }

    /**
     * 添加指定城市
     *
     * @param cityInfo 指定城市
     */
    public void addCity(CityInfo cityInfo){
        mSelectedCityDao.insertOrReplace(cityInfo2SelectedCity(cityInfo));
    }

    /**
     * 移除指定城市
     *
     * @param cityInfo 指定城市
     */
    public void removeCity(CityInfo cityInfo){
        removeCity(cityInfo.getCityId(), cityInfo.getLocation());
    }

    /**
     * 移除指定城市
     *
     * @param cityId 城市代码
     * @param location 城市名称
     */
    public void removeCity(String cityId, String location){
        Query<SelectedCity> query = mSelectedCityDao.queryBuilder()
                .where(SelectedCityDao.Properties.CityId.eq(cityId), SelectedCityDao.Properties.Location.eq(location))
                .build();
        List<SelectedCity> list = query.list();
        for (SelectedCity selectedCity : list) {
            mSelectedCityDao.deleteByKey(selectedCity.getId());
        }
    }

    private static SelectedCity cityInfo2SelectedCity(CityInfo cityInfo){
        SelectedCity selectedCity = new SelectedCity();
        selectedCity.setAdminArea(cityInfo.getAdminArea());
        selectedCity.setCityId(cityInfo.getCityId());
        selectedCity.setCountry(cityInfo.getCountry());
        selectedCity.setLatitude(cityInfo.getLatitude());
        selectedCity.setLongitude(cityInfo.getLongitude());
        selectedCity.setLocation(cityInfo.getLocation());
        selectedCity.setParentCity(cityInfo.getParentCity());
        selectedCity.setTimeZone(cityInfo.getTimeZone());
        selectedCity.setType(cityInfo.getType());
        return selectedCity;
    }
}
