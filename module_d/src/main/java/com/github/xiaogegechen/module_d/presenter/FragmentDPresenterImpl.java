package com.github.xiaogegechen.module_d.presenter;

import android.app.Activity;

import com.github.xiaogegechen.module_d.view.IFragmentDView;

import org.jetbrains.annotations.Nullable;

public class FragmentDPresenterImpl implements IFragmentDPresenter {

    private IFragmentDView mFragmentDView;
    private Activity mActivity;

    public FragmentDPresenterImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void gotoExpressActivity() {

    }

    @Override
    public void gotoBookActivity() {

    }

    @Override
    public void gotoVoiceActivity() {

    }

    @Override
    public int getToolsCount() {
        return 3;
    }

    @Override
    public void manageTools() {

    }

    @Override
    public void attach(@Nullable IFragmentDView fragmentDView) {
        mFragmentDView = fragmentDView;
    }

    @Override
    public void detach() {
        mFragmentDView = null;
    }
}
