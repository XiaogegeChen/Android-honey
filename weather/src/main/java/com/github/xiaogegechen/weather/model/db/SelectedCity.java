package com.github.xiaogegechen.weather.model.db;

import com.github.xiaogegechen.weather.model.CityInfo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 管理已选城市的表，不要直接操作数据库，通过{@link com.github.xiaogegechen.weather.helper.SelectedCitiesManager}
 * 提供的API实现操作
 */
@Entity(indexes = {
        @Index(value = "cityId", unique = true)
})
public class SelectedCity implements Comparable<SelectedCity>{
    /**
     * 自增id
     */
    @Id
    private Long id;

    // 地区／城市ID
    private String cityId;

    // 地区／城市名称
    private String location;

    // 该地区／城市的上级城市
    private String parentCity;

    // 该地区／城市所属行政区域
    private String adminArea;

    // 城市名
    private String country;

    // 地区／城市纬度
    private String latitude;

    // 地区／城市经度
    private String longitude;

    // 该地区／城市所在时区
    private String timeZone;

    // 该地区／城市的属性，目前有city（城市）和scenic（中国景点）
    private String type;

    public SelectedCity(){}

    @Generated(hash = 377657365)
    public SelectedCity(Long id, String cityId, String location, String parentCity,
            String adminArea, String country, String latitude, String longitude,
            String timeZone, String type) {
        this.id = id;
        this.cityId = cityId;
        this.location = location;
        this.parentCity = parentCity;
        this.adminArea = adminArea;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZone = timeZone;
        this.type = type;
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

    public String getParentCity() {
        return parentCity;
    }

    public void setParentCity(String parentCity) {
        this.parentCity = parentCity;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CityInfo convert2CityInfo(){
        CityInfo cityInfo = new CityInfo();
        cityInfo.setAdminArea(adminArea);
        cityInfo.setType(type);
        cityInfo.setTimeZone(timeZone);
        cityInfo.setParentCity(parentCity);
        cityInfo.setLongitude(longitude);
        cityInfo.setLocation(location);
        cityInfo.setLatitude(latitude);
        cityInfo.setCountry(country);
        cityInfo.setCityId(cityId);
        return cityInfo;
    }

    @Override
    public int compareTo(SelectedCity o) {
        if(this.cityId.equals(o.getCityId())){
            return 0;
        }
        return -1;
    }
}
