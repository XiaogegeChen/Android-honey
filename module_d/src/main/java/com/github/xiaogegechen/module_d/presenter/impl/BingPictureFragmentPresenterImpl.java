package com.github.xiaogegechen.module_d.presenter.impl;

import com.github.xiaogegechen.module_d.presenter.IBingPictureFragmentPresenter;
import com.github.xiaogegechen.module_d.view.IBingPictureFragmentView;

public class BingPictureFragmentPresenterImpl implements IBingPictureFragmentPresenter {

    private IBingPictureFragmentView mBingPictureFragmentView;

    @Override
    public void attach(IBingPictureFragmentView bingPictureFragmentView) {
        mBingPictureFragmentView = bingPictureFragmentView;
    }

    @Override
    public void detach() {
        mBingPictureFragmentView = null;
    }

    @Override
    public void queryModuleAndTopic() {

    }

    private void queryModule(){

    }
}
