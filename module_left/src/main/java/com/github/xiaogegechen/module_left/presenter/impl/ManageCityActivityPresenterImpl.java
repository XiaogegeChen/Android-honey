package com.github.xiaogegechen.module_left.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.github.xiaogegechen.module_left.presenter.IManageCityActivityPresenter;
import com.github.xiaogegechen.module_left.view.IManageCityActivityView;
import com.github.xiaogegechen.module_left.view.impl.SelectCityActivity;

public class ManageCityActivityPresenterImpl implements IManageCityActivityPresenter {

    private IManageCityActivityView mManageCityActivityView;
    private Activity mActivity;

    @Override
    public void attach(IManageCityActivityView manageCityActivityView) {
        mManageCityActivityView = manageCityActivityView;
        mActivity = (Activity) mManageCityActivityView;
    }

    @Override
    public void detach() {
        mManageCityActivityView = null;
    }

    @Override
    public void gotoSelectCityActivity() {
        Intent intent = new Intent(mActivity, SelectCityActivity.class);
        mActivity.startActivity(intent);
    }
}
