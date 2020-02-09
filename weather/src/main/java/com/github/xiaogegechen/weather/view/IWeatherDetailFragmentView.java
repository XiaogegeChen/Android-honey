package com.github.xiaogegechen.weather.view;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.weather.model.Air;
import com.github.xiaogegechen.weather.model.Forecast;
import com.github.xiaogegechen.weather.model.Hourly;
import com.github.xiaogegechen.weather.model.Lifestyle;

import java.util.List;

public interface IWeatherDetailFragmentView extends IBaseView {

    /**
     * 在视图中显示实时天气，包括头部的温度等和下面的空气状况
     *
     * @param tempText 温度
     * @param weatherDescriptionText 天气状况描述
     * @param weatherAirList 空气状况
     * @param condCode 天气状态码
     */
    void showNow(String tempText, String weatherDescriptionText, List<Air> weatherAirList, String condCode);

    /**
     * 在视图中显示逐小时天气。
     *
     * @param weatherHourlyList 数据源
     */
    void showHourly(List<Hourly> weatherHourlyList);

    /**
     * 在视图中显示未来几天的天气。
     *
     * @param weatherForecastList 数据源
     */
    void showForecast(List<Forecast> weatherForecastList);

    /**
     * 在视图中显示生活建议
     *
     * @param weatherLifestyleList 数据源
     */
    void showLifestyle(List<Lifestyle> weatherLifestyleList);

    /**
     * 显示刷新图标
     */
    void showSwipeRefresh();

    /**
     * 隐藏刷新图标
     */
    void hideSwipeRefresh();
}
