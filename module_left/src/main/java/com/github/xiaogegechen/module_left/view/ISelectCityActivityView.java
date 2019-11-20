package com.github.xiaogegechen.module_left.view;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.module_left.model.CityInfo;

import java.util.List;

public interface ISelectCityActivityView extends IBaseView {

    /**
     * 显示热门城市列表
     * @param cityInfoList 城市列表
     */
    void showTopCityList(List<CityInfo> cityInfoList);

    /**
     * 显示热门城市列表，这个方法没有参数，只控制ui的状态(哪个显示，那个不显示)，不进行数据填充
     */
    void showTopCityList();

    /**
     * 显示可能的城市列表
     * @param cityInfoList 城市列表
     */
    void showCityList(List<CityInfo> cityInfoList);

    /**
     * 显示可能的城市列表，这个方法没有参数，只控制ui的状态(哪个显示，那个不显示)，不进行数据填充
     */
    void showCityList();
}
