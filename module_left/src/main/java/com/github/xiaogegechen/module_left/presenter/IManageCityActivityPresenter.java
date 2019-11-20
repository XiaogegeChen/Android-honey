package com.github.xiaogegechen.module_left.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_left.view.IManageCityActivityView;

public interface IManageCityActivityPresenter extends IBasePresenter<IManageCityActivityView> {
    /**
     * 跳转到选择添加城市界面
     */
    void gotoSelectCityActivity();
}
