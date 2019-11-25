package com.github.xiaogegechen.module_left.view;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.module_left.model.WeatherAir;
import com.github.xiaogegechen.module_left.model.WeatherForecast;
import com.github.xiaogegechen.module_left.model.WeatherHourly;
import com.github.xiaogegechen.module_left.model.WeatherLifestyle;

import java.util.List;

public interface IWeatherDetailFragmentView extends IBaseView {

    /**
     * 在视图中显示实时天气，包括头部的温度等和下面的空气状况
     *
     * @param tempText 温度
     * @param weatherDescriptionText 天气状况描述
     * @param compareText 与昨天对比
     * @param weatherAirList 空气状况
     */
    void showNow(String tempText, String weatherDescriptionText, String compareText, List<WeatherAir> weatherAirList);

    /**
     * 在视图中显示逐小时天气。
     *
     * @param weatherHourlyList 数据源
     */
    void showHourly(List<WeatherHourly> weatherHourlyList);

    /**
     * 在视图中显示未来几天的天气。
     *
     * @param weatherForecastList 数据源
     */
    void showForecast(List<WeatherForecast> weatherForecastList);

    /**
     * 在视图中显示生活建议
     *
     * @param weatherLifestyleList 数据源
     */
    void showLifestyle(List<WeatherLifestyle> weatherLifestyleList);

    /**
     * 显示刷新图标
     */
    void showSwipeRefresh();

    /**
     * 隐藏刷新图标
     */
    void hideSwipeRefresh();
}
