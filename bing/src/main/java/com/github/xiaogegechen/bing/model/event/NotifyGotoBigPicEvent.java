package com.github.xiaogegechen.bing.model.event;

import android.widget.ImageView;

import com.github.xiaogegechen.bing.model.Image;

/**
 * 通知跳转到{@link com.github.xiaogegechen.bing.view.impl.BigPicActivity}
 */
public class NotifyGotoBigPicEvent {
    // 图片
    private Image mImage;
    // 共享元素
    private ImageView mImageView;

    public NotifyGotoBigPicEvent(Image image, ImageView imageView) {
        mImage = image;
        mImageView = imageView;
    }

    public Image getImage() {
        return mImage;
    }

    public ImageView getImageView() {
        return mImageView;
    }
}
