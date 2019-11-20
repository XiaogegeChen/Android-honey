package com.github.xiaogegechen.main.event;

public class NotifyMenuClickEvent {
    // 目前被选中的位置
    private int mCurrentSelectedPosition;
    // 上一个被选中的位置
    private int mLastSelectedPosition;

    public NotifyMenuClickEvent(int currentSelectedPosition, int lastSelectedPosition) {
        mCurrentSelectedPosition = currentSelectedPosition;
        mLastSelectedPosition = lastSelectedPosition;
    }

    public int getCurrentSelectedPosition() {
        return mCurrentSelectedPosition;
    }

    public int getLastSelectedPosition() {
        return mLastSelectedPosition;
    }
}
