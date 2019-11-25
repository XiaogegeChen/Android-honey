package com.github.xiaogegechen.module_left.view;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.module_left.model.CityInfo;

import java.util.List;

public interface IWeatherActivityView extends IBaseView {

    /**
     * 向viewPager中添加一个城市，如果这个城市已经在viewPaper中了，那么应该直接跳转到它的界面
     *
     * @param cityInfo 城市信息
     */
    void addCity2ViewPager(CityInfo cityInfo);

    /**
     * 批量添加城市到viewPager中，其中任何一个城市已经存在原来viewPager中就跳过它。
     *
     * @param cityInfoList 城市信息列表
     */
    void addCityList2ViewPager(List<CityInfo> cityInfoList);
}
