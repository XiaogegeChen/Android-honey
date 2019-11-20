package com.github.xiaogegechen.module_left.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_left.view.IWeatherActivityView;

public interface IWeatherActivityPresenter extends IBasePresenter<IWeatherActivityView> {
    /**
     * 跳转到管理城市界面
     */
    void gotoManageCityActivity();
}
