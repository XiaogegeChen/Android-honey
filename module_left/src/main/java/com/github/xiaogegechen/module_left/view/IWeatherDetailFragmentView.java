package com.github.xiaogegechen.module_left.view;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.module_left.model.WeatherAir;

import java.util.List;

public interface IWeatherDetailFragmentView extends IBaseView {

    /**
     * 在视图中显示实时天气，包括头部的温度等和下面的空气状况
     * @param tempText 温度
     * @param weatherDescriptionText 天气状况描述
     * @param compareText 与昨天对比
     * @param weatherAirList 空气状况
     */
    void showNow(String tempText, String weatherDescriptionText, String compareText, List<WeatherAir> weatherAirList);

}
