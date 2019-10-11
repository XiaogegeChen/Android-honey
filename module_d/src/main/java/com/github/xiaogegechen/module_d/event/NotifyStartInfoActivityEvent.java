package com.github.xiaogegechen.module_d.event;

import android.widget.ImageView;

import com.github.xiaogegechen.module_d.model.db.BookInDB;

/**
 * 通知跳转到{@link com.github.xiaogegechen.module_d.view.impl.BookInfoActivity}的事件
 */
public class NotifyStartInfoActivityEvent {
    /**
     * book
     */
    private BookInDB book;

    /**
     * 动画用
     */
    private ImageView imageView;

    public NotifyStartInfoActivityEvent(BookInDB book, ImageView imageView) {
        this.book = book;
        this.imageView = imageView;
    }

    public NotifyStartInfoActivityEvent() {
    }

    public BookInDB getBook() {
        return book;
    }

    public void setBook(BookInDB book) {
        this.book = book;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
