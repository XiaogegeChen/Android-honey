package com.github.xiaogegechen.bing.model;

import java.util.List;

/**
 * bing套图中用于描述一个module的数据结构
 */
public class Module {
    // module类型
    private String mType;
    // module标题
    private String mTitle;
    // module下所有topic
    private List<Topic> mTopicList;

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<Topic> getTopicList() {
        return mTopicList;
    }

    public void setTopicList(List<Topic> topicList) {
        mTopicList = topicList;
    }
}
