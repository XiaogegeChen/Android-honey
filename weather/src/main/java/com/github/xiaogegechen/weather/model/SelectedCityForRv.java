package com.github.xiaogegechen.weather.model;

/**
 * 首页已选中城市的RvBean
 */
public class SelectedCityForRv {
    // 城市名
    private String mName;
    // 是否被选中
    private boolean mIsSelected;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }
}
