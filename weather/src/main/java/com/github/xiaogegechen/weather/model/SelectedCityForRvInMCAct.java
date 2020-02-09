package com.github.xiaogegechen.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 已经选中city的bean，由{@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}
 * 使用
 */
public class SelectedCityForRvInMCAct implements Parcelable {
    // 地区名，如：北京，和平区等
    private String mLocation;
    // 城市代码，如：CN101070107
    private String mId;
    // 天气状况描述， 如：晴，多云等
    private String mWeatherDescription;
    // 天气代码，如：100，999等
    private String mWeatherCode;
    // 温度，如1，-20等
    private String mTemp;

    public SelectedCityForRvInMCAct() {}

    public SelectedCityForRvInMCAct(String location, String id) {
        mLocation = location;
        mId = id;
    }

    protected SelectedCityForRvInMCAct(Parcel in) {
        mLocation = in.readString();
        mId = in.readString();
        mWeatherDescription = in.readString();
        mWeatherCode = in.readString();
        mTemp = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLocation);
        dest.writeString(mId);
        dest.writeString(mWeatherDescription);
        dest.writeString(mWeatherCode);
        dest.writeString(mTemp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SelectedCityForRvInMCAct> CREATOR = new Creator<SelectedCityForRvInMCAct>() {
        @Override
        public SelectedCityForRvInMCAct createFromParcel(Parcel in) {
            return new SelectedCityForRvInMCAct(in);
        }

        @Override
        public SelectedCityForRvInMCAct[] newArray(int size) {
            return new SelectedCityForRvInMCAct[size];
        }
    };

    public String getWeatherCode() {
        return mWeatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        mWeatherCode = weatherCode;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getWeatherDescription() {
        return mWeatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        mWeatherDescription = weatherDescription;
    }

    public String getTemp() {
        return mTemp;
    }

    public void setTemp(String temp) {
        mTemp = temp;
    }

    @Override
    public String toString() {
        return "SelectedCityForRvInMCAct{" +
                "mLocation='" + mLocation + '\'' +
                ", mId='" + mId + '\'' +
                '}';
    }
}
