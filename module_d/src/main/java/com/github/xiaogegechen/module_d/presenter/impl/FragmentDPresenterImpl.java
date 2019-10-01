package com.github.xiaogegechen.module_d.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.github.xiaogegechen.module_d.presenter.IFragmentDPresenter;
import com.github.xiaogegechen.module_d.view.IFragmentDView;
import com.github.xiaogegechen.module_d.view.impl.BookListActivity;

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
        Intent intent = new Intent(mActivity, BookListActivity.class);
        mActivity.startActivity(intent);
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
