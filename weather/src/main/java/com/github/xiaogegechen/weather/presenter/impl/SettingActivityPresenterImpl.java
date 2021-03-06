package com.github.xiaogegechen.weather.presenter.impl;

import com.github.xiaogegechen.weather.helper.BooleanValueManager;
import com.github.xiaogegechen.weather.presenter.ISettingActivityPresenter;
import com.github.xiaogegechen.weather.view.ISettingActivityView;

public class SettingActivityPresenterImpl implements ISettingActivityPresenter {

    private ISettingActivityView mSettingActivityView;

    @Override
    public void attach(ISettingActivityView settingActivityView) {
        mSettingActivityView = settingActivityView;
    }

    @Override
    public void detach() {
        mSettingActivityView = null;
    }

    @Override
    public void setAllowBgChange(boolean allow) {
        BooleanValueManager.getInstance().setAllowBgChange(allow);
    }

    @Override
    public boolean isAllowBgChange() {
        return BooleanValueManager.getInstance().isAllowBgChange();
    }
}
