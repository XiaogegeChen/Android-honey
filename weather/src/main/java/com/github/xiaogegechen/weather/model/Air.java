package com.github.xiaogegechen.weather.model;

/**
 * 表示当前空气状况，以键值对形式保存
 */
public class Air {
    // 属性名，如：体感温度
    private String mName;
    // 属性值，如：18
    private String mValue;
    // 改属性被展示时的颜色资源
    private int mUIColorId;

    public Air(String name, String value) {
        mName = name;
        mValue = value;
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

    public int getUIColorId() {
        return mUIColorId;
    }

    public void setUIColorId(int UIColorId) {
        mUIColorId = UIColorId;
    }
}
