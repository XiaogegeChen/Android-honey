package com.github.xiaogegechen.main.model;

/**
 * 描述menuView的子View
 */
public class MenuItem {
    // 图标id
    private int mIconId;
    // 一般的颜色
    private int mNormalColor;
    // 选中后的颜色
    private int mSelectedColor;
    // 文字描述
    private String mDescription;

    public MenuItem(int iconId, int normalColor, int selectedColor, String description) {
        mIconId = iconId;
        mNormalColor = normalColor;
        mSelectedColor = selectedColor;
        mDescription = description;
    }

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }

    public int getNormalColor() {
        return mNormalColor;
    }

    public void setNormalColor(int normalColor) {
        mNormalColor = normalColor;
    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "mIconId=" + mIconId +
                ", mNormalColor=" + mNormalColor +
                ", mSelectedColor=" + mSelectedColor +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}
