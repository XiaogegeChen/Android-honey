package com.github.xiaogegechen.module_b.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_b.view.IFragmentBView;

public interface IFragmentBPresenter extends IBasePresenter<IFragmentBView> {
    /**
     * 跳转到星座详情Activity
     * @param constellationName 星座名
     */
    void gotoDetailActivity(String constellationName);
}
