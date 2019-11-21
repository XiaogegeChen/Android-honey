package com.github.xiaogegechen.module_left.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_left.view.IManageCityActivityView;

public interface IManageCityActivityPresenter extends IBasePresenter<IManageCityActivityView> {
    /**
     * 跳转到选择添加城市界面
     */
    void gotoSelectCityActivity();

    /**
     * 查询已经选中的城市列表信息并显示
     */
    void querySelectedCity();

    /**
     * 返回，包括按物理返回键和点击toolbar上的向导图标。需要先检查有没有城市被选中，如果没有则强制不能退出这个activity
     */
    void finish();
}
