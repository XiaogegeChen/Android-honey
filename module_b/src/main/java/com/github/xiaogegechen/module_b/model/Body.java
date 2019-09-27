package com.github.xiaogegechen.module_b.model;

/**
 * recyclerView body bean
 */
public class Body {
    private String title;
    private String content;
    private float rate;
    private int mIconId;

    public Body(String title, String content, float rate, int iconId) {
        this.title = title;
        this.content = content;
        this.rate = rate;
        mIconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public float getRate() {
        return rate;
    }

    public int getIconId() {
        return mIconId;
    }
}
