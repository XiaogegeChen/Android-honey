package com.github.xiaogegechen.design.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

import com.github.xiaogegechen.design.R;

public class ClickableTextView extends AppCompatTextView {

    private static final int AFTER_TEXT_COLOR_DEFAULT = Color.GRAY;
    private static final int AFTER_BG_COLOR_DEFAULT = Color.GRAY;

    @ColorInt private int mAfterTextColor;
    @ColorInt private int mAfterBgColor;

    // 有这个属性，说明我想设置背景改变的反馈效果。没有这个属性说明我不想更改背景，跳过
    private boolean mHasAfterBgColor;

    public ClickableTextView(Context context) {
        this(context, null);
    }

    public ClickableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickableTextView);
        mAfterTextColor = typedArray.getColor(R.styleable.ClickableTextView_ct_text_color_after, AFTER_TEXT_COLOR_DEFAULT);
        mHasAfterBgColor = typedArray.hasValue(R.styleable.ClickableTextView_ct_bg_color_after);
        if(mHasAfterBgColor){
            mAfterBgColor = typedArray.getColor(R.styleable.ClickableTextView_ct_bg_color_after, AFTER_BG_COLOR_DEFAULT);
        }
        typedArray.recycle();
    }

    private Drawable mBgDrawable;
    private int mTextColor;
    private int mBgColor;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isClickable()){
            setClickable(true);
        }
        int action = event.getActionMasked();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mTextColor = getCurrentTextColor();
                setTextColor(mAfterTextColor);
                if(mHasAfterBgColor){
                    mBgDrawable = getBackground();
                    if(mBgDrawable instanceof ColorDrawable){
                        mBgColor = ((ColorDrawable) mBgDrawable).getColor();
                    }
                    setBackgroundColor(mAfterBgColor);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setTextColor(mTextColor);
                if(mHasAfterBgColor){
                    if(mBgDrawable instanceof ColorDrawable){
                        setBackgroundColor(mBgColor);
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}

