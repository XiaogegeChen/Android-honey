package com.github.xiaogegechen.module_d.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_d.view.IFragmentDView;

public interface IFragmentDPresenter extends IBasePresenter<IFragmentDView> {
    /**
     * 跳转到快递查询页面
     */
    void gotoExpressActivity();

    /**
     * 跳转到读书页面
     */
    void gotoBookActivity();

    /**
     * 跳转到语音页面
     */
    void gotoVoiceActivity();

    /**
     * 拿到这个界面所有工具的数量
     * @return 工具总数
     */
    int getToolsCount();

    /**
     * 管理这个界面所有工具的图标
     */
    void manageTools();
}
