package com.github.xiaogegechen.module_b.model;

/**
 * recyclerView head innerRecyclerView bean
 */
public class HeadItem {
    private String title;
    private String content;

    public HeadItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
