package com.github.xiaogegechen.design.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.github.xiaogegechen.design.R;

public class ProgressButton extends View {
    //-----------------默认值-----------------------
    private static final int INIT_BG_COLOR = Color.parseColor("#1f7ffb");
    private static final int INIT_TEXT_COLOR = Color.parseColor("#ffffff");
    private static final int WORK_BG_COLOR = Color.parseColor("#f7f7f7");
    private static final int WORK_TEXT_COLOR = Color.parseColor("#1f7ffb");
    private static final int PROGRESS_COLOR = Color.parseColor("#1f7ffb");
    private static final int PROGRESS_TEXT_COLOR = Color.parseColor("#ffffff");
    private static final int TEXT_SIZE = 30;
    //-----------------属性值-----------------------
    private int mInitBgColor;
    private int mInitTextColor;
    private int mWorkBgColor;
    private int mWorkTextColor;
    private int mProgressColor;
    private int mProgressTextColor;
    private String mText;
    private int mProgress;
    // 标志位，工作状态用工作颜色，非工作状态用初始颜色
    private boolean mIsWorking;
    //-----------------画笔-----------------------
    private Paint mTextPaint;
    private Paint mBgPaint;
    private Paint mProgressPaint;
    private Path mBgPath;
    private Rect mTempRect;
    private Region mRegion;
    private Region mTempRegion;

    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton);
        mInitBgColor = ta.getColor(R.styleable.ProgressButton_pb_init_bg_color, INIT_BG_COLOR);
        mInitTextColor = ta.getColor(R.styleable.ProgressButton_pb_init_text_color, INIT_TEXT_COLOR);
        mWorkBgColor = ta.getColor(R.styleable.ProgressButton_pb_work_bg_color, WORK_BG_COLOR);
        mWorkTextColor = ta.getColor(R.styleable.ProgressButton_pb_work_text_color, WORK_TEXT_COLOR);
        mProgressColor = ta.getColor(R.styleable.ProgressButton_pb_progress_color, PROGRESS_COLOR);
        mProgressTextColor = ta.getColor(R.styleable.ProgressButton_pb_progress_text_color, PROGRESS_TEXT_COLOR);
        mText = ta.getString(R.styleable.ProgressButton_pb_text);
        mIsWorking = ta.getBoolean(R.styleable.ProgressButton_pb_work_mode, false);
        mProgress = ta.getInteger(R.styleable.ProgressButton_pb_work_progress, 0);
        float textSize = ta.getDimensionPixelSize(R.styleable.ProgressButton_pb_text_size, TEXT_SIZE);
        ta.recycle();

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mInitTextColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign (Paint.Align.CENTER);

        mBgPaint = new Paint();
        mBgPaint.setColor(mInitBgColor);
        mBgPaint.setStyle (Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(mProgressColor);
        mBgPaint.setStyle (Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);

        mBgPath = new Path();

        mRegion = new Region();
        mTempRect = new Rect();
        mTempRegion = new Region();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float r = (float) (height * 1.0 / 2);
        if(width > height){
            // 背景
            mBgPath.moveTo(r, 0);
            mBgPath.lineTo(width - r, 0);
            mBgPath.arcTo(width - r * 2, 0, width, height, 270, 180, false);
            mBgPath.lineTo(r, height);
            mBgPath.arcTo(0, 0, r * 2, r * 2, 90, 180, false);
            if(mIsWorking){
                mBgPaint.setColor(mWorkBgColor);
            }else{
                mBgPaint.setColor(mInitBgColor);
            }
            canvas.drawPath(mBgPath, mBgPaint);
            // 进度
            if(mIsWorking){
                mTempRegion.set(0, 0, (int) (width * mProgress * 1.0 / 100), height);
                mRegion.setPath(mBgPath, mTempRegion);
                drawRegion(mRegion, canvas, mProgressPaint, mTempRect);
            }
            // 文字
            float textX = (float) (width * 1.0 / 2);
            float textY = (float) (height * 1.0 / 2) - (mTextPaint.getFontMetrics ().top + mTextPaint.getFontMetrics ().bottom) / 2;
            if(mIsWorking){
                mTextPaint.getTextBounds(mText, 0, mText.length(), mTempRect);
                int textWidth = mTempRect.width();
                int textHeight = mTempRect.height();
                float textStartX = (float) (width * 1.0 / 2 - textWidth * 1.0 / 2);
                float textStartY = (float) (height * 1.0 / 2 - textHeight * 1.0 / 2);
                float textEndX = (float) (width * 1.0 / 2 + textWidth * 1.0 / 2);
                float textEndY = (float) (height * 1.0 / 2 + textHeight * 1.0 / 2);
                float progressWidth = (float) (width * mProgress * 1.0 / 100);
                // 文字位置
                if(textStartX < progressWidth && textEndX > progressWidth){
                    // 左右分开处理
                    // 左
                    canvas.save();
                    canvas.clipRect(textStartX, textStartY, progressWidth, textEndY);
                    mTextPaint.setColor(mProgressTextColor);
                    canvas.drawText(mText, textX, textY, mTextPaint);
                    canvas.restore();
                    // 右
                    canvas.save();
                    canvas.clipRect(progressWidth, textStartY, textEndX, textEndY);
                    mTextPaint.setColor(mWorkTextColor);
                    canvas.drawText(mText, textX, textY, mTextPaint);
                    canvas.restore();
                }else if(textStartX >= progressWidth){
                    // 不用左右分开处理
                    mTextPaint.setColor(mWorkTextColor);
                    canvas.drawText(mText, textX, textY, mTextPaint);
                }else if(textEndX <= progressWidth){
                    mTextPaint.setColor(mProgressTextColor);
                    canvas.drawText(mText, textX, textY, mTextPaint);
                }
            }else{
                // 初始状态
                mTextPaint.setColor(mInitTextColor);
                canvas.drawText(mText, textX, textY, mTextPaint);
            }
        }
    }

    public void setWorking(boolean working) {
        mIsWorking = working;
        invalidate();
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public void setText(String text) {
        mText = text;
        invalidate();
    }

    public void setInitBgColor(int initBgColor) {
        mInitBgColor = initBgColor;
        invalidate();
    }

    public void setInitTextColor(int initTextColor) {
        mInitTextColor = initTextColor;
        invalidate();
    }

    public void setWorkBgColor(int workBgColor) {
        mWorkBgColor = workBgColor;
        invalidate();
    }

    public void setWorkTextColor(int workTextColor) {
        mWorkTextColor = workTextColor;
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        invalidate();
    }

    public int getInitBgColor() {
        return mInitBgColor;
    }

    public int getInitTextColor() {
        return mInitTextColor;
    }

    public int getWorkBgColor() {
        return mWorkBgColor;
    }

    public int getWorkTextColor() {
        return mWorkTextColor;
    }

    public int getProgressColor() {
        return mProgressColor;
    }

    public int getProgressTextColor() {
        return mProgressTextColor;
    }

    public Paint getProgressPaint() {
        return mProgressPaint;
    }

    public String getText() {
        return mText;
    }

    public int getProgress() {
        return mProgress;
    }

    public boolean isWorking() {
        return mIsWorking;
    }

    public void setProgressTextColor(int progressTextColor) {
        mProgressTextColor = progressTextColor;
        invalidate();
    }

    private static void drawRegion(Region region, Canvas canvas, Paint paint, Rect iteratorRect){
        RegionIterator regionIterator = new RegionIterator(region);
        while (regionIterator.next(iteratorRect)){
            canvas.drawRect(iteratorRect, paint);
        }
    }
}

