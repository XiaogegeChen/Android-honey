package com.github.xiaogegechen.module_d.event;

import com.github.xiaogegechen.module_d.model.CatalogInfo;

/**
 * 通知图书列表刷新的事件，当目录被点击时发出，由
 * {@link com.github.xiaogegechen.module_d.view.impl.BookListActivity} 接收处理
 */
public class NotifyBookListRefreshEvent {
    // 目录
    private CatalogInfo mCatalogInfo;

    public NotifyBookListRefreshEvent(CatalogInfo catalogInfo) {
        mCatalogInfo = catalogInfo;
    }

    public CatalogInfo getCatalogInfo() {
        return mCatalogInfo;
    }

    public void setCatalogInfo(CatalogInfo catalogInfo) {
        mCatalogInfo = catalogInfo;
    }
}
