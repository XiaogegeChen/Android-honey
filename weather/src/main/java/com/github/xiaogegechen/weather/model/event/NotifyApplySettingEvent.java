package com.github.xiaogegechen.weather.model.event;

public class NotifyApplySettingEvent {
    private boolean mIsAllowBgChange;

    public boolean isAllowBgChange() {
        return mIsAllowBgChange;
    }

    public void setAllowBgChange(boolean allowBgChange) {
        mIsAllowBgChange = allowBgChange;
    }
}
