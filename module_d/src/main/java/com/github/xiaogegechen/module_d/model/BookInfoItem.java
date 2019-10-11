package com.github.xiaogegechen.module_d.model;

/**
 * {@link com.github.xiaogegechen.module_d.view.impl.BookInfoActivity}中的recyclerView
 * 的item bean
 */
public class BookInfoItem {

    private String mTitle;
    private String mContent;

    public BookInfoItem(String title, String content) {
        mTitle = title;
        mContent = content;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
