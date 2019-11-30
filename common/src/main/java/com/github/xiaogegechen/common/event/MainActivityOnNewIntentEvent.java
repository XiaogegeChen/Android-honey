package com.github.xiaogegechen.common.event;

import android.os.Parcelable;

/**
 * 用来描述一些activity返回主界面携带参数的数据结构。这种情况主界面通常只是起到消息的转发作用。通过eventBus实现
 */
public class MainActivityOnNewIntentEvent {
    // 来源，就是哪个activity返回主界面的，是Constants类中的常量
    private String mFrom;
    // 携带的参数
    private Parcelable[] mData;

    public MainActivityOnNewIntentEvent(String from, Parcelable[] data) {
        mFrom = from;
        mData = data;
    }

    public String getFrom() {
        return mFrom;
    }

    public Parcelable[] getData() {
        return mData;
    }
}
