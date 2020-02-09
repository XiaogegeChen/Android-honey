package com.github.xiaogegechen.design.viewgroup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * 佛系ToolBar，只是起到占位作用，不拦截，不消费触摸和点击事件
 */
public class PlaceholderToolBar extends Toolbar {
    public PlaceholderToolBar(Context context) {
        this(context, null);
    }

    public PlaceholderToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlaceholderToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
