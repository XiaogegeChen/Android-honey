package com.github.xiaogegechen.weather.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.view.IWeatherDetailFragmentView;

public interface IWeatherDetailFragmentPresenter extends IBasePresenter<IWeatherDetailFragmentView> {

    /**
     * 请求网络，查询指定城市的天气并显示
     * @param cityInfo 指定的城市
     */
    void queryWeather(CityInfo cityInfo);
}
