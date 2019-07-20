package com.github.xiaogegechen.module_b.model;

public class Constellation {

    private int iconId;
    private String name;
    private String date;

    public Constellation(int iconId, String name, String date) {
        this.iconId = iconId;
        this.name = name;
        this.date = date;
    }

    public int getIconId() {
        return iconId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
