package com.github.xiaogegechen.module_left.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.github.xiaogegechen.module_left.helper.SelectedCitySetHelper;
import com.github.xiaogegechen.module_left.model.CityInfo;
import com.github.xiaogegechen.module_left.model.SelectedCity;
import com.github.xiaogegechen.module_left.model.event.NotifyCityRemovedEvent;
import com.github.xiaogegechen.module_left.presenter.IManageCityActivityPresenter;
import com.github.xiaogegechen.module_left.view.IManageCityActivityView;
import com.github.xiaogegechen.module_left.view.impl.SelectCityActivity;

import java.util.List;

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
        // 最后提交更改
        SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).commit();
        mManageCityActivityView = null;
    }

    @Override
    public void gotoSelectCityActivity() {
        Intent intent = new Intent(mActivity, SelectCityActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    public void querySelectedCity() {
        if(SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).hasSelectedCity()){
            List<SelectedCity> selectedCityList = SelectedCitySetHelper
                    .getInstance(mActivity.getApplicationContext())
                    .getSelectedCity();
            // 显示
            mManageCityActivityView.showSelectedCity(selectedCityList);
        }else{
            // 没有添加任何城市
            mManageCityActivityView.showNothing();
        }
    }

    @Override
    public void handleNotifyCityRemovedEvent(NotifyCityRemovedEvent event) {
        int flag = event.getFlag();
        CityInfo cityInfo = event.getCityInfo();
        if(flag == NotifyCityRemovedEvent.FLAG_FROM_MANAGE_CITY_ACTIVITY){
            // 先移除city
            SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).removeCity(cityInfo);
            // 再移除recyclerView中对应的条目
            mManageCityActivityView.removeCity(new SelectedCity(cityInfo.getLocation(), cityInfo.getCityId()));
            // 如果所有都被移除了，显示空
            if(!SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).hasSelectedCity()){
                mManageCityActivityView.showNothing();
            }
        }
    }

    @Override
    public void finish() {
        // 最后提交更改
        SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).commit();
        if(!SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).hasSelectedCity()){
            mManageCityActivityView.showToast("请先选择一个城市");
        }else{
            mActivity.finish();
        }
    }
}
