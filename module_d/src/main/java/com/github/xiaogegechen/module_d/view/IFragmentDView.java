package com.github.xiaogegechen.module_d.view;

import com.github.xiaogegechen.common.base.IBaseView;

public interface IFragmentDView extends IBaseView {
    /**
     * 刷新视图中的工具数量，有两个view需要更新
     */
    void refreshCount();

    /**
     * 显示标题栏
     */
    void showTitle();

    /**
     * 隐藏标题栏
     */
    void hideTitle();
}
