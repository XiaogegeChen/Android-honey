package com.github.xiaogegechen.module_a.view;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.RequestListener;
import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.module_a.model.PictureItem;

import java.io.File;

public interface IBigPicActivityView extends IBaseView {
    /**
     * 显示原图，要先下载到缓存文件中，再使用Glide加载到UI
     *
     * @param pictureItem 图片信息
     */
    void showOriginalImage(PictureItem pictureItem);

    void showImageFromFile(File file, RequestListener<Drawable> listener);

    void showViewOriginalProgress(String progress);

    void hideViewOriginalButton();
}
