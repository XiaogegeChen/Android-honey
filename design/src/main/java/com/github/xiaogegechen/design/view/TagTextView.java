package com.github.xiaogegechen.design.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatTextView;

import com.github.xiaogegechen.design.R;

public class TagTextView extends AppCompatTextView {

    private static final String TAG = "TagTextView";

    private static final int BACKGROUND_COLOR_DEFAULT = Color.parseColor("#4967A3");

    private GradientDrawable mBackgroundGradientDrawable;
    private int mBackgroundColor;

    public TagTextView(Context context) {
        this(context, null);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        mBackgroundColor = typedArray.getColor(R.styleable.TagTextView_tag_text_view_background_color, BACKGROUND_COLOR_DEFAULT);
        typedArray.recycle();
        // 监听layout，layout结束一定能拿到高度，这时再设置圆角
        final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setBackgroundColorInternal(mBackgroundColor);
            }
        });
    }

    public void setBgColor(final int backgroundColor) {
        int height = getHeight();
        int radius = height / 2;
        // 要通过监听来设置
        if(height == 0){
            final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    setBackgroundColorInternal(backgroundColor);
                }
            });
        }else{
            // 直接设置
            setBackgroundColorInternal(backgroundColor);
        }
    }

    private void setBackgroundColorInternal(int backgroundColor){
        if (mBackgroundGradientDrawable == null) {
            mBackgroundGradientDrawable = new GradientDrawable();
            setBackground(mBackgroundGradientDrawable);
        }
        int height = getHeight();
        int radius = height / 2;
        mBackgroundGradientDrawable.setColor(backgroundColor);
        Log.d(TAG, "radius is : " + radius);
        mBackgroundGradientDrawable.setCornerRadius(radius);
        // 重绘
        invalidate();
    }
}