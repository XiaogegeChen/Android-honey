package com.github.xiaogegechen.module_b.model;

/**
 * recyclerView head bean
 */
public class Head {

    private Today mToday;
    private int mIconId;

    public Head(Today today, int iconId) {
        mToday = today;
        mIconId = iconId;
    }

    public Today getToday() {
        return mToday;
    }

    public int getIconId() {
        return mIconId;
    }
}
