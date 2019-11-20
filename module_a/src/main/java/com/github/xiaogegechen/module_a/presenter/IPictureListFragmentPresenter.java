package com.github.xiaogegechen.module_a.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_a.view.IPictureListFragmentView;
import com.github.xiaogegechen.module_a.view.impl.PictureListFragment;

public interface IPictureListFragmentPresenter extends IBasePresenter<IPictureListFragmentView> {
    /**
     * 请求图片和图片下面的短句
     * @param type 图片和短句的类型
     */
    void queryPictureAndSentence(@PictureListFragment.TabType int type);
}
