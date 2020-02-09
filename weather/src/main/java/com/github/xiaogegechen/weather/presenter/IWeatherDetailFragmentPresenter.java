package com.github.xiaogegechen.weather.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.view.IWeatherDetailFragmentView;

public interface IWeatherDetailFragmentPresenter extends IBasePresenter<IWeatherDetailFragmentView> {

    /**
     * 获取指定城市的天气并显示，会优先显示缓存的信息，如果缓存过期或者没有缓存则从网络拿。
     * @param cityInfo 指定的城市
     */
    void queryWeather(CityInfo cityInfo);

    /**
     * 直接从网络获取天气信息并显示
     * @param cityInfo 指定的城市
     */
    void queryWeatherFromNetwork(CityInfo cityInfo);
}
