package com.github.xiaogegechen.module_d.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_d.view.IBookListActivityView;

public interface IBookListActivityPresenter extends IBasePresenter<IBookListActivityView> {
    /**
     * 请求获取图书目录，由于有请求次数限制，而且结果也不经常变动，
     * 所以多次请求就显得很浪费，因此请求之后的值缓存进数据库，
     * 并设置一段时间以内固定从数据库拿，当超过这个时长，可以从网络
     * 拿，并重置这个时间节点（不是自己的服务器，省着点用哈！）
     */
    void queryCatalog();

    /**
     * 请求获取某个目录下的所有图书
     * @param catalogId 目录编号
     */
    void queryBookList(int catalogId);

    /**
     * 重新请求目录
     */
    void retryCatalog();

    /**
     * 重新请求图书列表
     * @param catalogId 目录id
     */
    void retryBookList(int catalogId);
}
