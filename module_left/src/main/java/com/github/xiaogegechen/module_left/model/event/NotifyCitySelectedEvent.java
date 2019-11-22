package com.github.xiaogegechen.module_left.model.event;

import com.github.xiaogegechen.module_left.model.CityInfo;

/**
 * 当一个城市被选中时将发送这个事件(两种情况，一种是topCity被选中，另一种是模糊搜索的city被选中，分开处理)
 * {@link com.github.xiaogegechen.module_left.view.impl.SelectCityActivity}，sp存取；
 * {@link com.github.xiaogegechen.module_left.view.impl.ManageCityActivity}，列表更新；
 * {@link com.github.xiaogegechen.module_left.view.impl.WeatherActivity}，增加对应城市的页面
 */
public class NotifyCitySelectedEvent {

    /**
     * 标志位，表示这个事件是选中热门城市时发出的
     */
    public static final int FLAG_FROM_TOP = 100;

    /**
     * 标志位，表示这个事件是选中模糊搜索的城市时发出的
     */
    public static final int FLAG_FROM_FIND = 101;

    private CityInfo mCityInfo;

    private int mFlag;

    public NotifyCitySelectedEvent(CityInfo cityInfo, int flag) {
        mCityInfo = cityInfo;
        mFlag = flag;
    }

    public int getFlag() {
        return mFlag;
    }

    public CityInfo getCityInfo() {
        return mCityInfo;
    }
}
