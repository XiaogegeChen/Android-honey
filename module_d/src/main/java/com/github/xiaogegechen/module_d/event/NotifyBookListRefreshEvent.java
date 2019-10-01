package com.github.xiaogegechen.module_d.event;

/**
 * 通知图书列表刷新的事件，当目录被点击时发出，由
 * {@link com.github.xiaogegechen.module_d.view.impl.BookListActivity} 接收处理
 */
public class NotifyBookListRefreshEvent {
    // 目录编号
    private int catalogId;

    public NotifyBookListRefreshEvent(int catalogId) {
        this.catalogId = catalogId;
    }

    public int getCatalogId() {
        return catalogId;
    }
}
