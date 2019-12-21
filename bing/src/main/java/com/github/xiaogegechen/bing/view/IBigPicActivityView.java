package com.github.xiaogegechen.bing.view;

import com.github.xiaogegechen.bing.model.Image;
import com.github.xiaogegechen.common.base.IBaseView;

public interface IBigPicActivityView extends IBaseView {

    /**
     * 显示指定图片的原图
     *
     * @param image 指定的图片
     */
    void showOrigin(Image image);

}
