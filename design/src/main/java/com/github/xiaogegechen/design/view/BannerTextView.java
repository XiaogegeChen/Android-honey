package com.github.xiaogegechen.design.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.github.xiaogegechen.design.R;
import com.github.xiaogegechen.design.Utils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BannerTextView extends FrameLayout {
    public static final int INVALID_POSITION = -1;

    private static final int TEXT_SIZE_DEFAULT = Utils.dp2px(18);
    private static final int TEXT_COLOR_DEFAULT = Color.BLACK;
    private static final int PADDING_DEFAULT = 0;
    private static final int GRAVITY_DEFAULT = Gravity.CENTER;
    private static final int TEXT_STYLE_DEFAULT = 0;
    private static final int MAX_LINES_DEFAULT = 1;
    private static final int MIN_LINES_DEFAULT = 1;
    private static final int ELLIPSIZE_DEFAULT = 0;
    private static final int ANIMATOR_DURATION_DEFAULT = 700;
    private static final int INTERVAL_DEFAULT = 3000;
    private static final Interpolator INTERPOLATOR_DEFAULT = new FastOutSlowInInterpolator();

    private int mAnimatorDuration;
    private int mInterval;
    private Interpolator mInterpolator = INTERPOLATOR_DEFAULT;

    private TextView mTextView1;
    private TextView mTextView2;

    private List<String> mDataSource;

    private Timer mTimer;
    private MyTimerTask mTimerTask;

    public BannerTextView(@NonNull Context context) {
        this(context, null);
    }

    public BannerTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.design_banner_text_view, this);
        mTextView1 = findViewById(R.id.text1);
        mTextView2 = findViewById(R.id.text2);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerTextView);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.BannerTextView_btv_textSize, TEXT_SIZE_DEFAULT);
        int textColor = typedArray.getColor(R.styleable.BannerTextView_btv_textColor, TEXT_COLOR_DEFAULT);
        int padding = typedArray.getDimensionPixelSize(R.styleable.BannerTextView_btv_padding, PADDING_DEFAULT);
        int paddingLeft = typedArray.getDimensionPixelSize(R.styleable.BannerTextView_btv_paddingLeft, PADDING_DEFAULT);
        int paddingRight = typedArray.getDimensionPixelSize(R.styleable.BannerTextView_btv_paddingRight, PADDING_DEFAULT);
        int paddingTop = typedArray.getDimensionPixelSize(R.styleable.BannerTextView_btv_paddingTop, PADDING_DEFAULT);
        int paddingBottom = typedArray.getDimensionPixelSize(R.styleable.BannerTextView_btv_paddingBottom, PADDING_DEFAULT);
        int gravity = typedArray.getInt(R.styleable.BannerTextView_btv_gravity, GRAVITY_DEFAULT);
        int textStyle = typedArray.getInt(R.styleable.BannerTextView_btv_textStyle, TEXT_STYLE_DEFAULT);
        int maxLine = typedArray.getInteger(R.styleable.BannerTextView_btv_maxLines, MAX_LINES_DEFAULT);
        int minLine = typedArray.getInteger(R.styleable.BannerTextView_btv_minLines, MIN_LINES_DEFAULT);
        int ellipsize = typedArray.getInt(R.styleable.BannerTextView_btv_ellipsize, ELLIPSIZE_DEFAULT);
        mAnimatorDuration = typedArray.getInteger(R.styleable.BannerTextView_btv_ani_duration, ANIMATOR_DURATION_DEFAULT);
        mInterval = typedArray.getInteger(R.styleable.BannerTextView_btv_interval, INTERVAL_DEFAULT);
        TextView[] textViews = new TextView[]{mTextView1, mTextView2};
        for (TextView textView : textViews) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            textView.setTextColor(textColor);
            textView.setPadding(
                    typedArray.hasValue(R.styleable.BannerTextView_btv_paddingLeft) ? paddingLeft : padding,
                    typedArray.hasValue(R.styleable.BannerTextView_btv_paddingTop) ? paddingTop : padding,
                    typedArray.hasValue(R.styleable.BannerTextView_btv_paddingRight) ? paddingRight : padding,
                    typedArray.hasValue(R.styleable.BannerTextView_btv_paddingBottom) ? paddingBottom : padding
            );
            textView.setGravity(gravity);
            textView.setTypeface(null, textStyle);
            textView.setMaxLines(maxLine);
            textView.setMinLines(minLine);
            switch (ellipsize) {
                case ELLIPSIZE_START:
                    textView.setEllipsize(TextUtils.TruncateAt.START);
                    break;
                case ELLIPSIZE_MIDDLE:
                    textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                    break;
                case ELLIPSIZE_NONE:
                case ELLIPSIZE_END:
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    break;
                case ELLIPSIZE_MARQUEE:
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    break;
            }
        }
        typedArray.recycle();
    }

    private static final int ELLIPSIZE_NONE = 0;
    private static final int ELLIPSIZE_START = 1;
    private static final int ELLIPSIZE_MIDDLE = 2;
    private static final int ELLIPSIZE_END = 3;
    private static final int ELLIPSIZE_MARQUEE = 4;

    /**
     * 设置动画时长
     * @param animatorDuration 动画时长
     */
    public void setAnimatorDuration(int animatorDuration) {
        mAnimatorDuration = animatorDuration;
    }

    /**
     * 设置切换时间间隔
     * @param interval 时间间隔
     */
    public void setInterval(int interval) {
        mInterval = interval;
    }

    /**
     * 设置动画插值器
     * @param interpolator 动画插值器
     */
    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    /**
     * 设置数据源
     * @param dataSource 数据源
     */
    public void setDataSource(List<String> dataSource) {
        mDataSource = dataSource;
    }

    /**
     * 从第一个开始轮播，当跟改完配置后要调用这个方法重新开始
     */
    public void start(){
        if(mDataSource != null && mDataSource.size() > 0){
            if(mDataSource.size() == 1){
                // 一个扩容成两个
                mDataSource.add(mDataSource.get(0));
            }
            if (mTimer != null) {
                mTimer.cancel();
            }
            mTimer = new Timer();
            mTimerTask = new MyTimerTask();
            mTimer.schedule(mTimerTask, mInterval, mInterval);
        }
    }

    /**
     * 停止动画，回收资源
     */
    public void stop(){
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            mTimerTask = null;
        }
    }

    /**
     * 拿到当前显示的数据在数据源中的位置
     * @return 示的数据在数据源中的位置，如果数据源不合法则返回无效值
     */
    public int getCurrentPosition(){
        if(mDataSource != null && mDataSource.size() > 0){
            if(mDataSource.size() == 1){
                return 0;
            }else{
                if (mTimerTask != null) {
                    return mTimerTask.mCurrentIndex;
                }
            }
        }
        return INVALID_POSITION;
    }

    private class MyTimerTask extends TimerTask{
        private TextView mCurrentTextView;
        private TextView mAnotherTextView;
        private ObjectAnimator mCurrentTextViewAnimator;
        // 上一个被使用的数据在数据源中的索引
        private int mLastUsedIndex;
        // 当前显示的数据在数据源中的索引
        int mCurrentIndex;

        MyTimerTask(){
            mCurrentTextView = mTextView2;
            mAnotherTextView = mTextView1;
            mCurrentTextView.setVisibility(VISIBLE);
            mAnotherTextView.setVisibility(INVISIBLE);
            mCurrentTextView.setText(mDataSource.get(0));
            mAnotherTextView.setText(mDataSource.get(1));
            mLastUsedIndex = 1;
            mCurrentIndex = 0;
        }

        @Override
        public void run() {
            // 主线程执行
            post(() -> {
                // 重新配置动画
                rebuildAnimator();
                mCurrentTextViewAnimator.start();
            });
        }

        private void rebuildAnimator(){
            mCurrentTextViewAnimator = ObjectAnimator.ofFloat(mCurrentTextView, TRANSLATION_Y, 0f, (float) (-1.0 * getHeight()));
            mCurrentTextViewAnimator.setDuration(mAnimatorDuration);
            mCurrentTextViewAnimator.setInterpolator(mInterpolator);
            mCurrentTextViewAnimator.addUpdateListener(animation -> {
                // 更新下面的textView的位置
                float mCurrTvTranY = (float) animation.getAnimatedValue();
                float mAnoTvTranY = mCurrTvTranY + getHeight();
                mAnotherTextView.setTranslationY(mAnoTvTranY);
            });
            mCurrentTextViewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    // 先移动到最下面
                    mAnotherTextView.setTranslationY(getHeight());
                    mAnotherTextView.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mCurrentTextView.setVisibility(INVISIBLE);
                    mCurrentTextView.setTranslationY(0);
                    // 交换，回归到原来的状态
                    TextView temp = mCurrentTextView;
                    mCurrentTextView = mAnotherTextView;
                    mAnotherTextView = temp;
                    mCurrentIndex = mLastUsedIndex;
                    if(mLastUsedIndex + 1 == mDataSource.size()){
                        mLastUsedIndex = 0;
                    }else{
                        mLastUsedIndex = mLastUsedIndex + 1;
                    }
                    mAnotherTextView.setText(mDataSource.get(mLastUsedIndex));
                }
            });
        }
    }
}
