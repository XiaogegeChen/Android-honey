package com.github.xiaogegechen.bing.presenter;

import com.github.xiaogegechen.bing.model.Image;
import com.github.xiaogegechen.bing.view.IBigPicActivityView;
import com.github.xiaogegechen.common.base.IBasePresenter;

public interface IBigPicActivityPresenter extends IBasePresenter<IBigPicActivityView> {
    /**
     * 下载指定的图片，只下载原图
     *
     * @param image 指定的图片
     */
    void download(Image image);

    /**
     * 分享指定的图片，分享的是当前显示的图片，可能是原图或者缩略图
     *
     * @param url 指定的图片url
     */
    void share(String url);

    /**
     * 查看原图
     *
     * @param image 指定的图片
     */
    void viewOrigin(Image image);
}
