package com.github.xiaogegechen.weather.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.weather.model.event.NotifyCityRemovedEvent;
import com.github.xiaogegechen.weather.view.IManageCityActivityView;

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
     * 处理由与点击删除按钮发出的 NotifyCityRemovedEvent 事件。需要从已选列表中移除，再从recyclerView中删除
     * @param event event
     */
    void handleNotifyCityRemovedEvent(NotifyCityRemovedEvent event);

    /**
     * 返回，包括按物理返回键和点击toolbar上的向导图标。需要先检查有没有城市被选中，如果没有则强制不能退出这个activity
     */
    void finish();
}
