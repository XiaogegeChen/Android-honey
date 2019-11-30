package com.github.xiaogegechen.weather.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.test.TestActivity;
import com.github.xiaogegechen.weather.helper.SelectedCitySetHelper;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.SelectedCity;
import com.github.xiaogegechen.weather.presenter.IWeatherFragmentPresenter;
import com.github.xiaogegechen.weather.view.IWeatherFragmentView;
import com.github.xiaogegechen.weather.view.impl.ManageCityActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WeatherFragmentPresenterImpl implements IWeatherFragmentPresenter {

    private IWeatherFragmentView mWeatherFragmentView;
    private Activity mActivity;

    @Override
    public void attach(IWeatherFragmentView weatherFragmentView) {
        mWeatherFragmentView = weatherFragmentView;
        mActivity = ((BaseFragment) weatherFragmentView).obtainActivity();
    }

    @Override
    public void detach() {
        mWeatherFragmentView = null;
    }

    @Override
    public void gotoManageCityActivity() {
        Intent intent = new Intent(mActivity, ManageCityActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    public void gotoManageCityActivityIfNeeded() {
        if(!SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).hasSelectedCity()){
            // 没有添加任何城市，先添加城市
            gotoManageCityActivity();
        }else{
            // 批量添加进viewPager
            List<SelectedCity> selectedCityList = SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).getSelectedCity();
            List<CityInfo> cityInfoList = new ArrayList<>();
            for (SelectedCity selectedCity : selectedCityList) {
                CityInfo cityInfo = new CityInfo();
                cityInfo.setCityId(selectedCity.getId());
                cityInfo.setLocation(selectedCity.getLocation());
                cityInfoList.add(cityInfo);
            }
            mWeatherFragmentView.addCityList2ViewPager(cityInfoList);
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
