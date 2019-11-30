package com.github.xiaogegechen.weather.model.event;

import com.github.xiaogegechen.weather.model.CityInfo;

/**
 * 当一个城市被选中取消选中时发送这个事件。需要接收通知的有
 * {@link com.github.xiaogegechen.weather.view.impl.SelectCityActivity}，sp存取；
 * {@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}，列表更新；
 * {@link com.github.xiaogegechen.weather.view.impl.WeatherActivity}，删除对应城市的页面
 */
public class NotifyCityRemovedEvent {

    /**
     * 标志位，表示这个事件是在{@link com.github.xiaogegechen.weather.view.impl.SelectCityActivity}中
     * 发生的
     */
    public static final int FLAG_FROM_SELECT_CITY_ACTIVITY = 100;

    /**
     * 标志位，表示这个事件是在{@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}中
     * 发生的
     */
    public static final int FLAG_FROM_MANAGE_CITY_ACTIVITY = 101;

    private int mFlag;

    private CityInfo mCityInfo;

    public NotifyCityRemovedEvent(CityInfo cityInfo, int flag) {
        mFlag = flag;
        mCityInfo = cityInfo;
    }

    public int getFlag() {
        return mFlag;
    }

    public CityInfo getCityInfo() {
        return mCityInfo;
    }
}
