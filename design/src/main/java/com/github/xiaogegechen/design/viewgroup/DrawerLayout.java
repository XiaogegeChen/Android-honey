package com.github.xiaogegechen.design.viewgroup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.github.xiaogegechen.design.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽屉式的layout，这个是从下向上的抽屉，其中至少包含两个子View，第一个是抽屉的头部，其它是抽屉的内容。
 */
public class DrawerLayout extends LinearLayout {

    public static final int OPEN = 0;
    public static final int CLOSE = 1;
    public static final int DRAGGED = 2;
    public static final int FLING = 3;

    @IntDef({
            OPEN,
            CLOSE,
            DRAGGED,
            FLING
    })
    public @interface Status{}

    // ---------------默认属性-------------
    private static final int MIN_HEIGHT_DEFAULT = Utils.dp2px(100);
    private static final float FACTOR_DEFAULT = 0.25f;
    private static final int DURATION_DEFAULT = 300;
    private static final Interpolator OPEN_INTERPOLATOR_DEFAULT = new FastOutSlowInInterpolator();
    private static final Interpolator CLOSE_INTERPOLATOR_DEFAULT = new AccelerateInterpolator();
    private static final float MAX_BG_ALPHA = 0.3f;

    // ---------------属性-------------
    // 最小高度，dp
    private int mMinHeight = MIN_HEIGHT_DEFAULT;
    // 临界因子
    private float mFactor = FACTOR_DEFAULT;
    // 打开动画时长
    private int mOpenAnimatorDuration = DURATION_DEFAULT;
    // 关闭动画时长
    private int mCloseAnimatorDuration = DURATION_DEFAULT;
    // 打开动画插值器
    private Interpolator mOpenAnimatorInterpolator = OPEN_INTERPOLATOR_DEFAULT;
    // 关闭动画插值器
    private Interpolator mCloseAnimatorInterpolator = CLOSE_INTERPOLATOR_DEFAULT;
    // 位置监听
    private List<OnTranslationYChangeListener> mOnTranslationYChangeListenerList = new ArrayList<>();
    // 背景view，用来衬托这个抽屉
    private View mBgView;
    // 背景view的最大透明度
    private float mBgViewMaxAlpha = MAX_BG_ALPHA;

    // 一个事件序列中上一个事件的Y坐标
    private float mLastY;
    // 标记是点击事件还是拖动事件，如果一个事件序列没有move事件，可以认定为点击事件
    private boolean mIsClick;
    // 当前状态
    private @Status int mCurrentStatus = OPEN;

    public DrawerLayout(@NonNull Context context) {
        this(context, null);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 添加一个默认的监听器
        addOnTranslationYChangeListener(new OnTranslationYChangeListener() {
            @Override
            public void onTranslationYChange(float currentTranslationY) {
                // 处于关闭状态时，背景view要设置为GONE
                if (mBgView != null) {
                    if(currentTranslationY == getMaxTranslationY()){
                        mBgView.setVisibility(GONE);
                    }else{
                        // 其它时刻背景view透明度随位置变化
                        if(mBgView.getVisibility() != VISIBLE){
                            mBgView.setVisibility(VISIBLE);
                        }
                        float rate = currentTranslationY / getMaxTranslationY();
                        mBgView.setAlpha((1 - rate) * mBgViewMaxAlpha);
                    }
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        View headView = getChildAt(0);
        // 如果在动画中不拦截
        if(mCurrentStatus == FLING){
            return false;
        }
        // 手指在 mHeadView 上直接拦截
        if(isTouchPointInView(headView, (int)ev.getRawX(), (int)ev.getRawY())) {
            return true;
        }else{
            // 不在 mHeadView 上不拦截
            return false;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mIsClick = true;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 更新状态
                mCurrentStatus = DRAGGED;
                // 一旦有move事件发生就不能判定为点击了
                mIsClick = false;
                // 跟手滑动
                float dy = y - mLastY;
                setTranslationY(getTranslationY() + dy);
                // 将其限制在一定范围内
                float currentTranslationY = getTranslationY();
                float maxTranslationY = getHeight() - mMinHeight;
                float minTranslationY = 0;
                if(currentTranslationY > maxTranslationY){
                    setTranslationY(maxTranslationY);
                }
                if(currentTranslationY < minTranslationY){
                    setTranslationY(minTranslationY);
                }
                // 通知监听器
                for(OnTranslationYChangeListener listener : mOnTranslationYChangeListenerList){
                    listener.onTranslationYChange(currentTranslationY);
                }
                // 触摸位置更新
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(mIsClick){
                    // 点击模式，要响应点击事件
                    // 找到这个view
                    View touchTarget = getTouchTarget(this, (int) event.getRawX(), (int) event.getRawY());
                    if (touchTarget != null) {
                        touchTarget.performClick();
                    }
                }else{
                    // 拖动事件，手离开后返回指定位置
                    if(getTranslationY() < getHeight() * mFactor){
                        // 打开到最大
                        open();
                    }else{
                        // 关闭到最小
                        close();
                    }
                }
                break;
            default:
                break;
        }
        // 消费这个事件序列
        return true;
    }

    @Override
    public void setVisibility(int visibility) {
        if(visibility != VISIBLE){
            // 隐藏bgView
            if (mBgView != null) {
                mBgView.setVisibility(GONE);
            }
        }
        super.setVisibility(visibility);
    }

    /**
     * 打开drawer，把drawer拉到最高
     */
    public void open(){
        open(null);
    }

    /**
     * 打开drawer，把drawer拉到最高
     *
     * @param callback 回调监听
     */
    public void open(AnimationCallback callback){
        // 已经打开不处理
        if(mCurrentStatus == OPEN){
            return;
        }
        // 正在动画中不处理
        if(mCurrentStatus == FLING){
            return;
        }
        // 正在动画中不处理
        // 抽屉打开的动画
        final ObjectAnimator openAnimator = ObjectAnimator.ofFloat(
                this,
                "translationY",
                getTranslationY(),
                0
        );
        // 时长
        openAnimator.setDuration(mOpenAnimatorDuration);
        // 插值器
        openAnimator.setInterpolator(mOpenAnimatorInterpolator);
        // 监听动画进度
        openAnimator.removeAllUpdateListeners();
        openAnimator.addUpdateListener(animation -> {
            for (OnTranslationYChangeListener listener : mOnTranslationYChangeListenerList) {
                listener.onTranslationYChange((float)(animation.getAnimatedValue()));
            }
        });
        openAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentStatus = FLING;
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCurrentStatus = OPEN;
                openAnimator.removeListener(this);
                if (callback != null) {
                    callback.onEnd();
                }
            }
        });
        // 开启动画
        openAnimator.start();
    }

    /**
     * 关闭drawer，把drawer拉到最低
     */
    public void close(){
        close(null);
    }

    /**
     * 关闭drawer，把drawer拉到最低，并监听关闭过程的动画
     *
     * @param callback 回调监听
     */
    public void close(AnimationCallback callback){
        // 已经关闭不处理
        if(mCurrentStatus == CLOSE){
            return;
        }
        // 正在动画中不处理
        if(mCurrentStatus == FLING){
            return;
        }
        // 抽屉关闭的动画
        final ObjectAnimator closeAnimator = ObjectAnimator.ofFloat(
                this,
                "translationY",
                getTranslationY(),
                getHeight() - mMinHeight
        );
        // 时长
        closeAnimator.setDuration(mCloseAnimatorDuration);
        // 插值器
        closeAnimator.setInterpolator(mCloseAnimatorInterpolator);
        // 监听动画进度
        closeAnimator.removeAllUpdateListeners();
        closeAnimator.addUpdateListener(animation -> {
            for (OnTranslationYChangeListener listener : mOnTranslationYChangeListenerList) {
                listener.onTranslationYChange((float)(animation.getAnimatedValue()));
            }
        });
        closeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentStatus = FLING;
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCurrentStatus = CLOSE;
                closeAnimator.removeListener(this);
                if (callback != null) {
                    callback.onEnd();
                }
            }
        });
        // 开启动画
        closeAnimator.start();
    }

    /**
     * 拿到最小高度，单位是dp
     *
     * @return 最小高度，单位是dp
     */
    public int getMinHeight() {
        return mMinHeight;
    }

    /**
     * 设置最小高度，单位是dp
     *
     * @param minHeight 最小高度，单位是dp
     */
    public void setMinHeight(int minHeight) {
        mMinHeight = minHeight;
    }

    /**
     * 拿到临界因子
     *
     * @return 临界因子
     */
    public float getFactor() {
        return mFactor;
    }

    /**
     * 设置临界因子
     *
     * @param factor 临界因子
     */
    public void setFactor(float factor) {
        mFactor = factor;
    }

    /**
     * 拿到打开动画的时长
     *
     * @return 打开动画的时长
     */
    public int getOpenAnimatorDuration() {
        return mOpenAnimatorDuration;
    }

    /**
     * 设置打开动画的时长
     *
     * @param openAnimatorDuration 时长
     */
    public void setOpenAnimatorDuration(int openAnimatorDuration) {
        mOpenAnimatorDuration = openAnimatorDuration;
    }

    /**
     * 拿到关闭动画的时长
     *
     * @return 关闭动画的时长
     */
    public int getCloseAnimatorDuration() {
        return mCloseAnimatorDuration;
    }

    /**
     * 设置关闭动画的时长
     *
     * @param closeAnimatorDuration 时长
     */
    public void setCloseAnimatorDuration(int closeAnimatorDuration) {
        mCloseAnimatorDuration = closeAnimatorDuration;
    }

    /**
     * 拿到打开动画的插值器
     *
     * @return 打开动画的插值器
     */
    public Interpolator getOpenAnimatorInterpolator() {
        return mOpenAnimatorInterpolator;
    }

    /**
     * 设置打开动画的插值器
     *
     * @param openAnimatorInterpolator 插值器
     */
    public void setOpenAnimatorInterpolator(Interpolator openAnimatorInterpolator) {
        mOpenAnimatorInterpolator = openAnimatorInterpolator;
    }

    /**
     * 拿到关闭动画的插值器
     *
     * @return 关闭动画的插值器
     */
    public Interpolator getCloseAnimatorInterpolator() {
        return mCloseAnimatorInterpolator;
    }

    /**
     * 设置关闭动画的插值器
     *
     * @param closeAnimatorInterpolator 插值器
     */
    public void setCloseAnimatorInterpolator(Interpolator closeAnimatorInterpolator) {
        mCloseAnimatorInterpolator = closeAnimatorInterpolator;
    }

    /**
     * 拿到背景view
     *
     * @return 背景view,如果没设置返回null
     */
    public View getBgView() {
        return mBgView;
    }

    /**
     * 设置背景view
     *
     * @param bgView 背景view
     */
    public void setBgView(View bgView) {
        mBgView = bgView;
    }

    /**
     * 设置背景view 和最大透明度
     *
     * @param bgView 背景view
     * @param bgViewMaxAlpha 最大透明度
     */
    public void setBgView(View bgView, float bgViewMaxAlpha){
        mBgView = bgView;
        mBgViewMaxAlpha = bgViewMaxAlpha;
    }

    /**
     * 拿到当前状态
     *
     * @return 当前状态
     */
    public @Status int getCurrentStatus() {
        return mCurrentStatus;
    }

    /**
     * 拿到 TranslationY 的最大值，也就是在完全关闭状态下的 TranslationY
     *
     * @return TranslationY 的最大值
     */
    public float getMaxTranslationY(){
        return getHeight() - mMinHeight;
    }

    /**
     * 添加位置监听
     *
     * @param onTranslationYChangeListener 位置监听器
     */
    public void addOnTranslationYChangeListener(OnTranslationYChangeListener onTranslationYChangeListener) {
        mOnTranslationYChangeListenerList.add(onTranslationYChangeListener);
    }

    /**
     * 移除位置监听
     *
     * @param onTranslationYChangeListener 位置监听器
     */
    public void removeOnTranslationYChangeListener(OnTranslationYChangeListener onTranslationYChangeListener) {
        mOnTranslationYChangeListenerList.remove(onTranslationYChangeListener);
    }

    /**
     * 移除所有位置监听，会把默认的监听器也一并移除，慎用！
     */
    public void removeAllOnTranslationYChangeListener(){
        mOnTranslationYChangeListenerList.clear();
    }

    /**
     * 监听动画状态的监听器
     */
    public interface AnimationCallback{
        /**
         * 开始
         */
        void onStart();

        /**
         * 结束
         */
        void onEnd();
    }

    /**
     * 监听视图当前的位置
     */
    public interface OnTranslationYChangeListener{
        /**
         * 当TranslationY发生变化时会回调这个方法
         *
         * @param currentTranslationY 变化后的 TranslationY，在完全打开时是0，完全关闭时最大
         */
        void onTranslationYChange(float currentTranslationY);
    }

    private static boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left && x <= right;
    }

    private static View getTouchTarget(View view, int x, int y) {
        View targetView = null;
        // 判断view是否可以聚焦
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                targetView = child;
                break;
            }
        }
        return targetView;
    }
}
