package com.github.xiaogegechen.module_b.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_b.view.IConstellationDetailActivityView;

public interface IConstellationDetailActivityPresenter extends IBasePresenter<IConstellationDetailActivityView> {

    /**
     * 回到入口页
     */
    void back();

    /**
     * 分享
     */
    void share();

    /**
     * 每日运势
     */
    void queryToday();

    /**
     * 周运势
     */
    void queryWeek();

    /**
     * 年运势
     */
    void queryYear();

    /**
     * 重新加载
     */
    void retry();

    /**
     * 取消加载
     */
    void cancel();
}

