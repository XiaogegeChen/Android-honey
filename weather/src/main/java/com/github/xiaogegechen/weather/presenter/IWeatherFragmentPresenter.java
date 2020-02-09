package com.github.xiaogegechen.weather.presenter;

import androidx.annotation.Nullable;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.weather.model.SelectedCityForRvInMCAct;
import com.github.xiaogegechen.weather.view.IWeatherFragmentView;

import java.util.List;

public interface IWeatherFragmentPresenter extends IBasePresenter<IWeatherFragmentView> {
    /**
     * 跳转到{@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}
     *
     * @param selectedCityForRvInMCActList 携带的参数，可能是空的，不携带任何参数
     */
    void gotoManageCityActivity(@Nullable List<SelectedCityForRvInMCAct> selectedCityForRvInMCActList);

    /**
     * onCreate 时检查有没有已经添加的城市，如果没有则需要直接跳转到{@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}
     * 选择城市。如果有则为每个城市添加fragment到viewPager中
     */
    void gotoManageCityActivityIfNeeded();

    /**
     * 跳转到{@link com.github.xiaogegechen.weather.view.impl.SelectCityActivity}，这个方法先跳转到
     * {@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}，再跳转到
     * {@link com.github.xiaogegechen.weather.view.impl.SelectCityActivity}
     */
    void gotoSelectedCityActivity();

    /**
     * 向服务端查询每日一图并显示
     */
    void queryDayPic();

    /**
     * 跳转到设置界面
     */
    void gotoSetting();

    /**
     * 向服务端请求轮播图内容
     */
    void queryBannerViewContent();

    /**
     * 查询热门城市列表
     */
    void queryHotCity();
}

