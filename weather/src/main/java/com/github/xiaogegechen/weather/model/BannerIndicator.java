package com.github.xiaogegechen.weather.model;

import androidx.annotation.IdRes;

/**
 * 轮播图下面的指示器（不是轮播图自身的指示器）的数据源
 */
public class BannerIndicator {
    // 名字
    private String mName;
    // 图标id
    private @IdRes int mIconId;
    // 是否被选中
    private boolean mIsSelected;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }
}
