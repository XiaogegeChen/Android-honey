package com.github.xiaogegechen.module_a.model;

/**
 * fragment_a 中的recyclerView 的子项
 */
public class PictureItem {

    // 图片url
    private String mUrl;

    // 图片的描述
    private String mDescription;

    public PictureItem(String url, String description) {
        mUrl = url;
        mDescription = description;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDescription() {
        return mDescription;
    }
}
