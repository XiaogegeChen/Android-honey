package com.github.xiaogegechen.module_left.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.github.xiaogegechen.common.test.TestActivity;
import com.github.xiaogegechen.common.util.XmlIOUtil;
import com.github.xiaogegechen.module_left.Constants;
import com.github.xiaogegechen.module_left.helper.SelectedCitySetHelper;
import com.github.xiaogegechen.module_left.presenter.IWeatherActivityPresenter;
import com.github.xiaogegechen.module_left.view.IWeatherActivityView;
import com.github.xiaogegechen.module_left.view.impl.ManageCityActivity;

import java.util.Set;

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
    public void addCity() {
        Intent intent = new Intent(mActivity, ManageCityActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    public void gotoManageCityActivityIfNeeded() {
        if(!SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).hasSelectedCity()){
            // 没有添加任何城市，先添加城市
            addCity();
        }
    }

    @Override
    public void debug() {
        // 查看sp中存储的已添加城市
        Set<String> selectedCityStringSet = SelectedCitySetHelper
                .getInstance(mActivity.getApplicationContext())
                .getSelectedCitySet();
        if(selectedCityStringSet.isEmpty()){
            TestActivity.startDebug(mActivity, "no city selected");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (String s : selectedCityStringSet) {
            builder.append(s);
            builder.append("\n");
        }
        TestActivity.startDebug(mActivity, builder.toString());
    }
}
