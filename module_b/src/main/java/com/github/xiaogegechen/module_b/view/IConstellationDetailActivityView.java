package com.github.xiaogegechen.module_b.view;

import com.github.xiaogegechen.common.base.IBaseView;

import java.util.List;

public interface IConstellationDetailActivityView extends IBaseView {
    /**
     * 显示数据，覆盖原数据
     * @param dataList 数据集合
     */
    void overrideAndShow(List<Object> dataList);

    /**
     * 在末尾追加数据并显示
     * @param dataList 数据集合
     */
    void addAndShow(List<Object> dataList);

    /**
     * 在头部插入数据并显示
     * @param object 数据
     */
    void addToHeadAndShow(Object object);

    /**
     * 关闭进度显示
     */
    void hideProgress();
}
