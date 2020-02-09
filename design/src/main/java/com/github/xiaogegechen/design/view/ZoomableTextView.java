package com.github.xiaogegechen.design.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.widget.AppCompatTextView;

import com.github.xiaogegechen.design.R;

/**
 * 触摸时可放缩的textView，按下时缩小，松开后放大
 *
 */
public class ZoomableTextView extends AppCompatTextView {
    private static final float MIN_DEFAULT = .97f;
    private static final float MAX_DEFAULT = 1f;
    private static final int DURATION_DEFAULT = 100;
    private static final Interpolator INTERPOLATOR_DEFAULT = new LinearInterpolator();

    private float mMax;
    private float mMin;
    private int mDuration;
    private Interpolator mInterpolator;

    private ObjectAnimator mZoomOutAnimator;
    private ObjectAnimator mZoomInAnimator;

    public ZoomableTextView(Context context) {
        this(context, null);
    }

    public ZoomableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZoomableTextView);
        mMax = typedArray.getFloat(R.styleable.ZoomableTextView_zt_max, MAX_DEFAULT);
        mMin = typedArray.getFloat(R.styleable.ZoomableTextView_zt_min, MIN_DEFAULT);
        mDuration = typedArray.getInteger(R.styleable.ZoomableTextView_zt_duration, DURATION_DEFAULT);
        typedArray.recycle();
        mInterpolator = INTERPOLATOR_DEFAULT;

        configAnimator();
    }

    private void configAnimator(){
        mZoomOutAnimator = ObjectAnimator.ofPropertyValuesHolder(
                this,
                PropertyValuesHolder.ofFloat(SCALE_X, mMax, mMin),
                PropertyValuesHolder.ofFloat(SCALE_Y, mMax, mMin));
        mZoomOutAnimator.setDuration(mDuration);
        mZoomOutAnimator.setInterpolator(mInterpolator);
        mZoomInAnimator = ObjectAnimator.ofPropertyValuesHolder(
                this,
                PropertyValuesHolder.ofFloat(SCALE_X, mMin, mMax),
                PropertyValuesHolder.ofFloat(SCALE_Y, mMin, mMax));
        mZoomInAnimator.setDuration(mDuration);
        mZoomInAnimator.setInterpolator(mInterpolator);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isClickable()){
            setClickable(true);
        }
        int action = event.getActionMasked();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mZoomOutAnimator.start();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mZoomInAnimator.start();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setInterpolator(Interpolator interpolator) {
        if(mInterpolator != interpolator){
            mInterpolator = interpolator;
            configAnimator();
        }
    }

    public void setMax(float max) {
        if(mMax != max){
            mMax = max;
            configAnimator();
        }
    }

    public void setMin(float min) {
        if(mMin != min){
            mMin = min;
            configAnimator();
        }
    }

    public void setDuration(int duration) {
        if(mDuration != duration){
            mDuration = duration;
            configAnimator();
        }
    }
}

