package com.github.xiaogegechen.weather.model;

public class Setting {
    private String mName;
    private boolean mSwitchVisibility;
    private boolean mSwitchChecked;

    public Setting(){
    }

    public Setting(String name, boolean switchVisibility, boolean switchChecked) {
        mName = name;
        mSwitchVisibility = switchVisibility;
        mSwitchChecked = switchChecked;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isSwitchVisibility() {
        return mSwitchVisibility;
    }

    public void setSwitchVisibility(boolean switchVisibility) {
        mSwitchVisibility = switchVisibility;
    }

    public boolean isSwitchChecked() {
        return mSwitchChecked;
    }

    public void setSwitchChecked(boolean switchChecked) {
        mSwitchChecked = switchChecked;
    }
}
