package com.github.xiaogegechen.weather.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.weather.model.event.NotifyCityRemovedEvent;
import com.github.xiaogegechen.weather.model.event.NotifySelectedCityRvInMCActItemClickEvent;
import com.github.xiaogegechen.weather.view.IManageCityActivityView;

public interface IManageCityActivityPresenter extends IBasePresenter<IManageCityActivityView> {
    /**
     * 跳转到选择添加城市界面
     */
    void gotoSelectCityActivity();

    /**
     * 查询已经选中的城市列表信息并和现有列表做比较，如果有新添加的要请求网络，拿到这个新添加城市的天气状况。这个方法更新UI
     */
    void querySelectedCity();

    /**
     * 处理由与点击删除按钮发出的 NotifyCityRemovedEvent 事件。需要从已选列表中移除，再从recyclerView中删除
     * @param event event
     */
    void handleNotifyCityRemovedEvent(NotifyCityRemovedEvent event);

    /**
     * 处理由于点击rv子项发出的 NotifySelectedCityRvInMCActItemClickEvent 事件，需要跳转到主界面并定位到指定页
     * @param event
     */
    void handleNotifySelectedCityRvInMCActItemClickEvent(NotifySelectedCityRvInMCActItemClickEvent event);

    /**
     * 返回，包括按物理返回键和点击toolbar上的向导图标。需要先检查有没有城市被选中，如果没有则强制不能退出这个activity
     */
    void finish();
}
