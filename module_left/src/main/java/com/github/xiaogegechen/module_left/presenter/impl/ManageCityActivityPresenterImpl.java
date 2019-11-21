package com.github.xiaogegechen.module_left.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.github.xiaogegechen.module_left.helper.SelectedCitySetHelper;
import com.github.xiaogegechen.module_left.model.SelectedCity;
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
    public void finish() {
        if(!SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).hasSelectedCity()){
            mManageCityActivityView.showToast("请先选择一个城市");
        }
        mActivity.finish();
    }
}
