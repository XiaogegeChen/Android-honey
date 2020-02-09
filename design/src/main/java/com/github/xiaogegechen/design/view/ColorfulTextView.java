package com.github.xiaogegechen.design.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

import com.github.xiaogegechen.design.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 颜色渐变的TextView
 */
public class ColorfulTextView extends AppCompatTextView {
    private static final int INTERVAL_DEFAULT = 100; // ms
    private static final int PERIOD_DEFAULT = 3000; // ms
    private static final int FROM_COLOR_DEFAULT = Color.RED;
    private static final int TO_COLOR_DEFAULT = Color.BLUE;

    private Timer mTimer;

    @ColorInt private int mFromColor;
    @ColorInt private int mToColor;
    private int mInterval;
    private int mPeriod;

    public ColorfulTextView(Context context) {
        this(context, null);
    }

    public ColorfulTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorfulTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorfulTextView);
        mFromColor = typedArray.getColor(R.styleable.ColorfulTextView_cftv_from_color, FROM_COLOR_DEFAULT);
        mToColor = typedArray.getColor(R.styleable.ColorfulTextView_cftv_to_color, TO_COLOR_DEFAULT);
        mPeriod = typedArray.getInteger(R.styleable.ColorfulTextView_cftv_period, PERIOD_DEFAULT);
        mInterval = typedArray.getInteger(R.styleable.ColorfulTextView_cftv_interval, INTERVAL_DEFAULT);
        typedArray.recycle();

    }

    public void setFromColor(@ColorInt int fromColor) {
        mFromColor = fromColor;
    }

    public void setToColor(@ColorInt int toColor) {
        mToColor = toColor;
    }

    public void setPeriod(int period) {
        mPeriod = period;
    }

    public void setInterval(int interval) {
        mInterval = interval;
    }

    public void rebuild(){
        start();
    }

    public void start(){
        setTextColor(mFromColor);
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = new Timer();
        mTimer.schedule(new MyTimerTask(), INTERVAL_DEFAULT, INTERVAL_DEFAULT);
    }

    public void stop(){
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private class MyTimerTask extends TimerTask{
        private int[] mFromColorRGBArr;
        private int[] mToColorRGBArr;

        private int mCount;
        private float mRStep;
        private float mGStep;
        private float mBStep;

        private float mCurrR;
        private float mCurrG;
        private float mCurrB;
        private int mCurrCount;

        private boolean mIsReverse;

        MyTimerTask(){
            int fromR = (mFromColor >> 16) & 0xff;
            int fromG = (mFromColor >> 8) & 0xff;
            int fromB = mFromColor & 0xff;

            int toR = (mToColor >> 16) & 0xff;
            int toG = (mToColor >> 8) & 0xff;
            int toB = mToColor & 0xff;

            mFromColorRGBArr = new int[]{fromR, fromG, fromB};
            mToColorRGBArr = new int[]{toR, toG, toB};
            mCount = mPeriod / mInterval;

            mRStep = (float) ((toR - fromR) * 1.0 / mCount);
            mGStep = (float) ((toG - fromG) * 1.0 / mCount);
            mBStep = (float) ((toB - fromB) * 1.0 / mCount);

            mCurrR = fromR;
            mCurrG = fromG;
            mCurrB = fromB;
            mCurrCount = 0;
            mIsReverse = false;
        }

        @Override
        public void run() {
            // 合成新颜色
            if(mIsReverse){
                mCurrR = mCurrR - mRStep;
                mCurrG = mCurrG - mGStep;
                mCurrB = mCurrB - mBStep;
                mCurrCount --;
            }else{
                mCurrR = mCurrR + mRStep;
                mCurrG = mCurrG + mGStep;
                mCurrB = mCurrB + mBStep;
                mCurrCount ++;
            }
            int newColor = (round(mCurrR) << 16) | (round(mCurrG) << 8) | round(mCurrB);
            int currTvColor = getCurrentTextColor();
            final int newTvColor = ((currTvColor >> 24) << 24) | newColor;
            // 设置颜色
            if(newTvColor != currTvColor){
                post(() -> setTextColor(newTvColor));
            }
            if(mCurrCount == mCount && !mIsReverse){
                mIsReverse = true;
                mCurrR = mToColorRGBArr[0];
                mCurrG = mToColorRGBArr[1];
                mCurrB = mToColorRGBArr[2];
            }
            if(mCurrCount == 0 && mIsReverse){
                mIsReverse = false;
                mCurrR = mFromColorRGBArr[0];
                mCurrG = mFromColorRGBArr[1];
                mCurrB = mFromColorRGBArr[2];
            }
        }
    }

    private static int round(float value){
        if (value < 0x00){
            return 0x00;
        }
        if (value > 0xff){
            return 0xff;
        }
        return (int) value;
    }
}

