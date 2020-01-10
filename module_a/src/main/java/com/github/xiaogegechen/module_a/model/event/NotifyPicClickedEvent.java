package com.github.xiaogegechen.module_a.model.event;

import android.widget.ImageView;

import com.github.xiaogegechen.module_a.model.PictureItem;

/**
 * 通知图片被点击的事件，在缩略图被点击时发出，由{@link com.github.xiaogegechen.module_a.view.impl.FragmentA}
 * 接收并处理
 */
public class NotifyPicClickedEvent {
    // 被点击的图片信息
    private PictureItem mPictureItem;
    // 被点击的图片
    private ImageView mImageView;

    public PictureItem getPictureItem() {
        return mPictureItem;
    }

    public void setPictureItem(PictureItem pictureItem) {
        mPictureItem = pictureItem;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }
}
