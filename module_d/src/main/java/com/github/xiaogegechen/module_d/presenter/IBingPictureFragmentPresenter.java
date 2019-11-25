package com.github.xiaogegechen.module_d.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_d.view.IBingPictureFragmentView;

public interface IBingPictureFragmentPresenter extends IBasePresenter<IBingPictureFragmentView> {
    /**
     * 先拿到module列表，再请求每一个module的topic并展示
     */
    void queryModuleAndTopic();
}
