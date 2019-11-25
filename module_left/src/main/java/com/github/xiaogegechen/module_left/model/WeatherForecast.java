package com.github.xiaogegechen.module_left.model;

/**
 * 未来几天的天气数据
 */
public class WeatherForecast {
    // 时间相对今天的描述，如：今天，明天
    private String mTimeDescription;
    // 时间，如：2019/11/23
    private String mTime;
    // 天气代码，如：101
    private String mCode;
    // 天气状况描述，如：小雪
    private String mConditionDescription;
    // 最高温度，如：23
    private String mTempMax;
    // 最低温度，如：-10
    private String mTempMin;

    public String getTimeDescription() {
        return mTimeDescription;
    }

    public void setTimeDescription(String timeDescription) {
        mTimeDescription = timeDescription;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getConditionDescription() {
        return mConditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        mConditionDescription = conditionDescription;
    }

    public String getTempMax() {
        return mTempMax;
    }

    public void setTempMax(String tempMax) {
        mTempMax = tempMax;
    }

    public String getTempMin() {
        return mTempMin;
    }

    public void setTempMin(String tempMin) {
        mTempMin = tempMin;
    }
}
