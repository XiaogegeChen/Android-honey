package com.github.xiaogegechen.weather.model.event;

import com.github.xiaogegechen.weather.model.CityInfo;

/**
 * 当一个城市被选中取消选中时发送这个事件。
 *
 * 这个事件是 recyclerView 中item被点击时发出的，通过标志位{@link #mFlag}判断是哪个 recyclerView 发出的。
 * flag为{@link #FLAG_FROM_MANAGE_CITY_ACTIVITY}表示是{@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}
 * 中的 recyclerView 发出的，flag为{@link #FLAG_FROM_SELECT_CITY_ACTIVITY}表示是{@link com.github.xiaogegechen.weather.view.impl.SelectCityActivity}
 * 中的 recyclerView 发出的。
 *
 * Note: 从相应activity发出的事件只能由相应的activity接收，从而降低耦合
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
