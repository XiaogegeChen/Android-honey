package com.github.xiaogegechen.module_a.presenter;

import androidx.annotation.IntDef;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.design.view.ProgressButton;
import com.github.xiaogegechen.module_a.model.PictureItem;
import com.github.xiaogegechen.module_a.view.IBigPicActivityView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IBigPicActivityPresenter extends IBasePresenter<IBigPicActivityView> {

    // 下载标志位
    int FLAG_DOWN_REAL = 0;
    int FLAG_DOWN_COMPRESS = 1;
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            FLAG_DOWN_REAL,
            FLAG_DOWN_COMPRESS
    })
    @interface DownloadFlag{}

    /**
     * 下载图片
     *
     * @param url 图片url
     * @param flag 标志位，表示下载的是原图还是缩略图
     * @param  progressButton 展示下载进度的按钮
     */
    void download(String url, @DownloadFlag int flag, ProgressButton progressButton);

    /**
     * 分享指定的图片，分享的是当前显示的图片，可能是原图或者缩略图
     *
     * @param url 指定的图片url
     */
    void share(String url);

    /**
     * 查看原图，先下载到缓存文件夹中，在UI上显示
     *
     * @param pictureItem 指定的图片
     */
    void viewOrigin(PictureItem pictureItem);
}
