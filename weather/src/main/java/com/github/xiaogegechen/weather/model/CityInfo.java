package com.github.xiaogegechen.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 详细的城市信息
 */
public class CityInfo implements Parcelable {
    // 地区／城市ID
    @SerializedName("cid")
    private String cityId;

    // 地区／城市名称
    @SerializedName("location")
    private String location;

    // 该地区／城市的上级城市
    @SerializedName("parent_city")
    private String parentCity;

    // 该地区／城市所属行政区域
    @SerializedName("admin_area")
    private String adminArea;

    // 城市名
    @SerializedName("cnty")
    private String country;

    // 地区／城市纬度
    @SerializedName("lat")
    private String latitude;

    // 地区／城市经度
    @SerializedName("lon")
    private String longitude;

    // 该地区／城市所在时区
    @SerializedName("tz")
    private String timeZone;

    // 该地区／城市的属性，目前有city（城市）和scenic（中国景点）
    @SerializedName("type")
    private String type;

    // 是否已经被选中
    private boolean selected;

    public CityInfo(){}

    protected CityInfo(Parcel in) {
        cityId = in.readString();
        location = in.readString();
        parentCity = in.readString();
        adminArea = in.readString();
        country = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        timeZone = in.readString();
        type = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<CityInfo> CREATOR = new Creator<CityInfo>() {
        @Override
        public CityInfo createFromParcel(Parcel in) {
            return new CityInfo(in);
        }

        @Override
        public CityInfo[] newArray(int size) {
            return new CityInfo[size];
        }
    };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityId);
        dest.writeString(location);
        dest.writeString(parentCity);
        dest.writeString(adminArea);
        dest.writeString(country);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(timeZone);
        dest.writeString(type);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "cityId='" + cityId + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
