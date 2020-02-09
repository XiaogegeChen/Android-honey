package com.github.xiaogegechen.design.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.github.xiaogegechen.design.R;

/**
 * 圆角imageView
 */
public class CornerImageView extends AppCompatImageView {

    private Path mPath;

    private int mLeftTop;
    private int mLeftBottom;
    private int mRightTop;
    private int mRightBottom;

    public CornerImageView(Context context) {
        this(context, null);
    }

    public CornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerImageView);
        mLeftTop = typedArray.getDimensionPixelSize(R.styleable.CornerImageView_corner_image_left_top, 0);
        mLeftBottom = typedArray.getDimensionPixelSize(R.styleable.CornerImageView_corner_image_left_bottom, 0);
        mRightTop = typedArray.getDimensionPixelSize(R.styleable.CornerImageView_corner_image_right_top, 0);
        mRightBottom = typedArray.getDimensionPixelSize(R.styleable.CornerImageView_corner_image_right_bottom, 0);
        typedArray.recycle();
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        mPath.lineTo(0, mLeftTop);
        mPath.arcTo(0, 0, mLeftTop * 2, mLeftTop * 2, 180, 90, false);
        mPath.lineTo(width - mRightTop, 0);
        mPath.arcTo(width - (mRightTop * 2), 0, width, mRightTop * 2, 270, 90, false);
        mPath.lineTo(width, height - mRightBottom);
        mPath.arcTo(width - (mRightBottom * 2), height - (mRightBottom * 2), width, height, 0, 90, false);
        mPath.lineTo(width - mLeftBottom, height);
        mPath.arcTo(0, height - (mLeftBottom * 2), mLeftBottom * 2, height, 90, 90, false);
        mPath.close();
        canvas.clipPath(mPath);
        canvas.save();
        super.onDraw(canvas);
        canvas.restore();
    }
}
