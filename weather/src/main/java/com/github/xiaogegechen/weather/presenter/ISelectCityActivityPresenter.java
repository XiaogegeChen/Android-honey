package com.github.xiaogegechen.weather.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.view.ISelectCityActivityView;

public interface ISelectCityActivityPresenter extends IBasePresenter<ISelectCityActivityView> {
    /**
     * 请求热门城市列表
     */
    void queryTopCityList();

    /**
     * 将指定城市移除，类似与事务，presenter内部维护城市列表，在activity结束时统一进行io操作
     * @param cityInfo 城市信息
     */
    void removeCity(CityInfo cityInfo);

    /**
     * 将城市加入到sp中
     * @param cityInfo 城市信息
     */
    void addCity(CityInfo cityInfo);

    /**
     * 处理输入框的输入。实现功能：
     * 1. 如果输入框是空的，显示热门城市列表
     * 2. 如果不是空的，筛选出中文，请求网络进行模糊查找
     * @param text 输入文本
     */
    void handleInput(String text);

    /**
     * 通过已经输入的部分请求可能的城市列表
     * @param input 输入
     */
    void queryCityList(String input);
}
