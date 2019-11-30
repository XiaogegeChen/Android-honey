package com.github.xiaogegechen.bing.presenter;

import com.github.xiaogegechen.bing.view.IBingFragmentView;
import com.github.xiaogegechen.common.base.IBasePresenter;

public interface IBingFragmentPresenter extends IBasePresenter<IBingFragmentView> {
    /**
     * 先拿到module列表，再请求每一个module的topic并展示
     */
    void queryModuleAndTopic();
}
