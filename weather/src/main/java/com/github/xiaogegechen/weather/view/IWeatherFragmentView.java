package com.github.xiaogegechen.weather.view;

import androidx.annotation.IntDef;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.weather.model.CityInfo;

import java.util.List;

public interface IWeatherFragmentView extends IBaseView {
    int TYPE_PRECIPITATION = 100;
    int TYPE_TEMPERATURE = 101;
    int TYPE_VISIBILITY = 102;
    int TYPE_WIND = 103;
    int TYPE_SATELLITE = 104;

    @IntDef({
            TYPE_PRECIPITATION,
            TYPE_TEMPERATURE,
            TYPE_VISIBILITY,
            TYPE_WIND,
            TYPE_SATELLITE
    })
    @interface BannerItemType{
    }

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

    /**
     * 向轮播图中添加一个图
     *
     * @param dataSource 待添加的数据源
     * @param dataType 数据类型
     */
    void addPicToBannerView(String dataSource, int dataType);

    /**
     * 显示热门城市
     *
     * @param hotCities 热门城市列表
     */
    void showHotCities(List<String> hotCities);
}