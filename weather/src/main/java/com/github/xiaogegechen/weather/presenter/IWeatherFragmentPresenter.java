package com.github.xiaogegechen.weather.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.weather.view.IWeatherFragmentView;

public interface IWeatherFragmentPresenter extends IBasePresenter<IWeatherFragmentView> {
    /**
     * 跳转到{@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}
     */
    void gotoManageCityActivity();

    /**
     * onCreate 时检查有没有已经添加的城市，如果没有则需要直接跳转到{@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}
     * 选择城市。如果有则为每个城市添加fragment到viewPager中
     */
    void gotoManageCityActivityIfNeeded();
}

