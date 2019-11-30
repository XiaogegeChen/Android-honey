package com.github.xiaogegechen.weather.model;

public class Hourly {
    // 温度，如：9
    private String mTemp;
    // 天气状况代码，如：305
    private String mCode;
    // 天气状况描述，如：阵雨
    private String mDescription;
    // 更新时间，如：2019-11-23 13:00
    private String mTime;

    public String getTemp() {
        return mTemp;
    }

    public void setTemp(String temp) {
        mTemp = temp;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }
}
