package com.github.xiaogegechen.module_left.model;

/**
 * 生活建议
 */
public class WeatherLifestyle {
    // 图标id
    private int mIconId;
    // 属性名
    private String mName;
    // 属性值
    private String mValue;
    // 详细建议
    private String Detail;

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }
}
