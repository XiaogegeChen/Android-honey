package com.github.xiaogegechen.module_d.model;

import java.util.List;

/**
 * bing套图中用于描述一个topic的数据结构
 */
public class BingPictureTopic {
    // 所属module
    private String mModuleType;
    // 标题
    private String mTitle;
    // 封面url
    private String mCoverUrl;
    // 这个topic下所有图片
    private List<BingPictureImage> mImageList;

    public List<BingPictureImage> getImageList() {
        return mImageList;
    }

    public void setImageList(List<BingPictureImage> imageList) {
        mImageList = imageList;
    }

    public String getModuleType() {
        return mModuleType;
    }

    public void setModuleType(String moduleType) {
        mModuleType = moduleType;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        mCoverUrl = coverUrl;
    }
}
