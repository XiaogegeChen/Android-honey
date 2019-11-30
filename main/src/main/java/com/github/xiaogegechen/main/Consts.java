package com.github.xiaogegechen.main;

import com.github.xiaogegechen.main.model.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Consts {
    public static final List<MenuItem> MENU_ITEM_LIST;

    private static final int NORMAL_COLOR = R.color.main_color_primary;
    private static final int SELECTED_COLOR = R.color.main_color_primary_dark;

    static {
        MENU_ITEM_LIST = new ArrayList<>();
        MENU_ITEM_LIST.add(new MenuItem(R.drawable.main_ic_cancel, NORMAL_COLOR, SELECTED_COLOR, "关闭"));
        MENU_ITEM_LIST.add(new MenuItem(R.drawable.main_ic_photo_wall, NORMAL_COLOR, SELECTED_COLOR, "照片墙"));
        MENU_ITEM_LIST.add(new MenuItem(R.drawable.main_ic_star, NORMAL_COLOR, SELECTED_COLOR, "星座"));
        MENU_ITEM_LIST.add(new MenuItem(R.drawable.main_ic_study, NORMAL_COLOR, SELECTED_COLOR, "学习"));
        MENU_ITEM_LIST.add(new MenuItem(R.drawable.main_ic_tool, NORMAL_COLOR, SELECTED_COLOR, "小工具"));
        MENU_ITEM_LIST.add(new MenuItem(R.drawable.main_ic_bing_pic, NORMAL_COLOR, SELECTED_COLOR, "精彩世界"));
        MENU_ITEM_LIST.add(new MenuItem(R.drawable.main_ic_weather, NORMAL_COLOR, SELECTED_COLOR, "天气"));
        Collections.reverse(MENU_ITEM_LIST);
    }
}
