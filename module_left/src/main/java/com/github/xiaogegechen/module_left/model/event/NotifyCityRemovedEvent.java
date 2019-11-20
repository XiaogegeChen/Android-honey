package com.github.xiaogegechen.module_left.model.event;

import com.github.xiaogegechen.module_left.model.CityInfo;

/**
 * 当一个城市被选中取消选中时发送这个事件。需要接收通知的有
 * {@link com.github.xiaogegechen.module_left.view.impl.SelectCityActivity}，sp存取；
 * {@link com.github.xiaogegechen.module_left.view.impl.ManageCityActivity}，列表更新；
 * {@link com.github.xiaogegechen.module_left.view.impl.WeatherActivity}，删除对应城市的页面
 */
public class NotifyCityRemovedEvent {

    private CityInfo mCityInfo;

    public NotifyCityRemovedEvent(CityInfo cityInfo) {
        mCityInfo = cityInfo;
    }

    public CityInfo getCityInfo() {
        return mCityInfo;
    }
}
