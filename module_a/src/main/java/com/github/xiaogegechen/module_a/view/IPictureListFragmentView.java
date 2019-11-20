package com.github.xiaogegechen.module_a.view;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.module_a.model.PictureItem;

import java.util.List;

public interface IPictureListFragmentView extends IBaseView {
    /**
     * 在recyclerView中显示图片列表
     * @param pictureItemList pictureItemList
     */
    void showPictureList(List<PictureItem> pictureItemList);

    /**
     * 显示已经加载完成
     */
    void showDone();
}
