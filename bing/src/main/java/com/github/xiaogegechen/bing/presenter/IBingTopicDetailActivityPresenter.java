package com.github.xiaogegechen.bing.presenter;

import com.github.xiaogegechen.bing.model.Topic;
import com.github.xiaogegechen.bing.view.IBingTopicDetailActivityView;
import com.github.xiaogegechen.common.base.IBasePresenter;

public interface IBingTopicDetailActivityPresenter extends IBasePresenter<IBingTopicDetailActivityView> {
    /**
     * 请求网络拿到一个topic下的所有图片并展示
     *
     * @param topic 这个topic的详细信息
     */
    void queryImageList(Topic topic);

}
