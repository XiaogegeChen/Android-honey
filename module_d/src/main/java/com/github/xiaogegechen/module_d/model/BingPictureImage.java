package com.github.xiaogegechen.module_d.model;

/**
 * bing套图中用于描述一个图片的数据结构
 */
public class BingPictureImage {
    // 图片所属module
    private String mModuleType;
    // 图片所属topic
    private String mTopicType;
    // 图片缩略图url
    private String mThumbnailUrl;
    // 图片大图url
    private String mRealUrl;
    // 图片来源网站连接
    private String mFromUrl;

    public String getModuleType() {
        return mModuleType;
    }

    public void setModuleType(String moduleType) {
        mModuleType = moduleType;
    }

    public String getTopicType() {
        return mTopicType;
    }

    public void setTopicType(String topicType) {
        mTopicType = topicType;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }

    public String getRealUrl() {
        return mRealUrl;
    }

    public void setRealUrl(String realUrl) {
        mRealUrl = realUrl;
    }

    public String getFromUrl() {
        return mFromUrl;
    }

    public void setFromUrl(String fromUrl) {
        mFromUrl = fromUrl;
    }
}
