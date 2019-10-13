package com.github.xiaogegechen.module_d.model;

/**
 * {@link com.github.xiaogegechen.module_d.adapter.ExpressMotionAdapter}
 */
public class ExpressMotionItem {

    /**
     * 时间
     */
    private String mTime;

    /**
     * 精确时间
     */
    private String mExactTime;

    /**
     * 详细信息描述
     */
    private String mInfo;

    /**
     * 当前图标id
     */
    private int mIconId;

    /**
     * 当前颜色，最新记录是黑色的，以往的是灰色的
     */
    private int mColor;

    /**
     * 分割线的颜色，最后一个是不需要显示分割线的，颜色为透明
     * 其它情况下和 mColor 一致
     */
    private int mSplitColor;

    public int getSplitColor() {
        return mSplitColor;
    }

    public void setSplitColor(int splitColor) {
        mSplitColor = splitColor;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getExactTime() {
        return mExactTime;
    }

    public void setExactTime(String exactTime) {
        mExactTime = exactTime;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }
}
