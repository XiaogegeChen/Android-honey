package com.github.xiaogegechen.module_left.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.github.xiaogegechen.module_left.presenter.IWeatherActivityPresenter;
import com.github.xiaogegechen.module_left.view.IWeatherActivityView;
import com.github.xiaogegechen.module_left.view.impl.ManageCityActivity;

public class WeatherActivityPresenterImpl implements IWeatherActivityPresenter {

    private IWeatherActivityView mWeatherActivityView;
    private Activity mActivity;

    @Override
    public void attach(IWeatherActivityView weatherActivityView) {
        mWeatherActivityView = weatherActivityView;
        mActivity = (Activity) mWeatherActivityView;
    }

    @Override
    public void detach() {
        mWeatherActivityView = null;
    }

    @Override
    public void gotoManageCityActivity() {
        Intent intent = new Intent(mActivity, ManageCityActivity.class);
        mActivity.startActivity(intent);
    }
}
