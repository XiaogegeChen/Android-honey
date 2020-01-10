package com.github.xiaogegechen.module_a.model.event;

import com.github.xiaogegechen.design.view.ProgressButton;
import com.github.xiaogegechen.module_a.model.PackingItem;

/**
 * 当大图页面的选择项被点击时发送该事件
 */
public class NotifyPackingItemClickedEvent {
    // 被点击的item的数据源
    private PackingItem mPackingItem;
    // 被点击的item
    private ProgressButton mProgressButton;

    public PackingItem getPackingItem() {
        return mPackingItem;
    }

    public void setPackingItem(PackingItem packingItem) {
        mPackingItem = packingItem;
    }

    public ProgressButton getProgressButton() {
        return mProgressButton;
    }

    public void setProgressButton(ProgressButton progressButton) {
        mProgressButton = progressButton;
    }
}
