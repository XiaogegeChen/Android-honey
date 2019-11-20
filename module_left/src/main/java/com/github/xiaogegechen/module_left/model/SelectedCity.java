package com.github.xiaogegechen.module_left.model;

/**
 * 已经选中city的bean，由{@link com.github.xiaogegechen.module_left.view.impl.ManageCityActivity}
 * 使用
 */
public class SelectedCity {
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

    public SelectedCity() {}

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
}
