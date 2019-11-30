package com.github.xiaogegechen.bing.view;

import com.github.xiaogegechen.bing.model.Image;
import com.github.xiaogegechen.common.base.IBaseView;

import java.util.List;

public interface IBingTopicDetailActivityView extends IBaseView {

    /**
     * 在recyclerView中显示图片
     *
     * @param imageList 图片数据源
     */
    void showImageList(List<Image> imageList);

}
