package com.github.xiaogegechen.common.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;

import androidx.annotation.RequiresApi;

/**
 * 动画工具类，参考官方文档
 * https://developer.android.google.cn/training/animation/reveal-or-hide-view#java
 */
public class AnimatorUtils {

    // ---------------------------- 淡入淡出动画------------------
    /**
     * 设置淡入淡出动画，适用于一个View替换另一个View的情况，注意淡出的view状态被设置为{@link View#GONE}
     * 这个方法没有指定动画时长，默认值为 config_shortAnimTime， 200ms
     * @param outView 即将淡出的view
     * @param inView 淡入的view
     * @param context 用于获取系统资源 config_shortAnimTime 的上下文
     *
     * @see #crossFade(View, View, int)
     */
    public static void crossFade(View outView, View inView, Context context){
        // 默认的duration
        final int defaultDuration = context.getResources().getInteger(android.R.integer.config_shortAnimTime);
        crossFade(outView, inView, defaultDuration);
    }

    /**
     * 设置淡入淡出动画，适用于一个View替换另一个View的情况,注意淡出的view状态被设置为{@link View#GONE}
     * @param outView 即将淡出的view
     * @param inView 淡入的view
     * @param duration 动画时长， 进出动画时长相等
     *
     * @see #crossFade(View, View, Context)
     */
    public static void crossFade(View outView, View inView, int duration){
        // inView 可见，但是透明
        inView.setAlpha(0f);
        inView.setVisibility(View.VISIBLE);

        // outView 逐渐消失
        final ViewPropertyAnimator outViewPropertyAnimator = outView.animate()
                .alpha(0f)
                .setDuration(duration);
        // 监听淡出动画，淡出结束要把outView置为GONE,并移除添加的动画监听
        outViewPropertyAnimator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                outView.setVisibility(View.GONE);
                outViewPropertyAnimator.setListener(null);
            }
        });

        // inView 逐渐出现
        inView.animate()
                .alpha(1f)
                .setDuration(duration);
    }

    // ---------------------------- 圆形揭示动画------------------
    /**
     * 通过圆形揭示动画显示一个View，默认的从view中心开始
     * @param targetView 待显示的view
     *
     * @see #displayByCircularReveal(View, int)
     * @see #displayByCircularReveal(View, int, int, int)
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void displayByCircularReveal(View targetView){
        int cx = targetView.getWidth() / 2;
        int cy = targetView.getHeight() / 2;
        int r = Math.max(cx, cy);
        displayByCircularReveal(targetView, cx, cy, r);
    }

    /**
     * 通过圆形揭示动画显示一个View，从指定位置的点开始向四周揭示，位置通过{@link Gravity}表示
     * @param targetView  待显示的view
     * @param centerPointGravity 动画起始点位置
     *
     * @see #displayByCircularReveal(View)
     * @see #displayByCircularReveal(View, int, int, int)
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void displayByCircularReveal(View targetView, int centerPointGravity){
        int cx;
        int cy;
        // 竖直方向
        final int verticalGravity = centerPointGravity & Gravity.VERTICAL_GRAVITY_MASK;
        if(verticalGravity == Gravity.TOP){
            cy = 0;
        }else if(verticalGravity == Gravity.BOTTOM){
            cy = targetView.getHeight();
        }else{ // verticalGravity == Gravity.CENTER_VERTICAL
            cy = targetView.getHeight() / 2;
        }
        // 水平方向
        final int horizontalGravity = centerPointGravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        if(horizontalGravity == Gravity.LEFT){
            cx = 0;
        }else if(horizontalGravity == Gravity.RIGHT){
            cx = targetView.getWidth();
        }else{ // horizontalGravity == Gravity.CENTER_HORIZONTAL
            cx = targetView.getWidth() / 2;
        }
        int r = Math.max(cx, cy);
        displayByCircularReveal(targetView, cx, cy, r);
    }

    /**
     * 通过圆形揭示动画显示一个View，从指定位置的点开始向四周揭示，位置通过坐标表示
     * @param targetView 待显示的view
     * @param cx 起始点X坐标（相对targetView）
     * @param cy 起始点Y坐标（相对targetView）
     * @param r 覆盖半径，从起始点开始，可以覆盖整个targetView的半径
     *
     * @see #displayByCircularReveal(View, int)
     * @see #displayByCircularReveal(View)
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void displayByCircularReveal(View targetView, int cx, int cy, int r){
        Animator animator = ViewAnimationUtils.createCircularReveal(targetView, cx, cy, 0, r);
        targetView.setVisibility(View.VISIBLE);
        animator.start();
    }

    /**
     * 通过圆形揭示动画隐藏一个View，默认的向targetView中心收缩
     * @param targetView 待隐藏的view
     *
     * @see #hideByCircularReveal(View, int)
     * @see #hideByCircularReveal(View, int, int, int)
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void hideByCircularReveal(final View targetView){
        int cx = targetView.getWidth() / 2;
        int cy = targetView.getHeight() / 2;
        int r = Math.max(cx, cy);
        hideByCircularReveal(targetView, cx, cy, r);
    }

    /**
     * 通过圆形揭示动画隐藏一个View 向指定位置收缩，位置通过{@link Gravity}表示
     * @param targetView 待隐藏的view
     * @param centerPointGravity 收缩结束点位置
     *
     * @see #hideByCircularReveal(View)
     * @see #hideByCircularReveal(View, int, int, int)
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void hideByCircularReveal(final View targetView, int centerPointGravity){
        int cx;
        int cy;
        // 竖直方向
        final int verticalGravity = centerPointGravity & Gravity.VERTICAL_GRAVITY_MASK;
        if(verticalGravity == Gravity.TOP){
            cy = 0;
        }else if(verticalGravity == Gravity.BOTTOM){
            cy = targetView.getHeight();
        }else{ // verticalGravity == Gravity.CENTER_VERTICAL
            cy = targetView.getHeight() / 2;
        }
        // 水平方向
        final int horizontalGravity = centerPointGravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        if(horizontalGravity == Gravity.LEFT){
            cx = 0;
        }else if(horizontalGravity == Gravity.RIGHT){
            cx = targetView.getWidth();
        }else{ // horizontalGravity == Gravity.CENTER_HORIZONTAL
            cx = targetView.getWidth() / 2;
        }
        int r = Math.max(cx, cy);
        hideByCircularReveal(targetView, cx, cy, r);
    }

    /**
     * 通过圆形揭示动画隐藏一个View 向指定位置收缩，位置通过坐标表示
     * @param targetView 待隐藏的view
     * @param cx 收缩结束点X坐标（相对targetView）
     * @param cy 收缩结束点Y坐标（相对targetView）
     * @param r 覆盖半径，可以覆盖整个targetView的半径
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void hideByCircularReveal(final View targetView, int cx, int cy, int r){
        Animator animator = ViewAnimationUtils.createCircularReveal(targetView, cx, cy, r, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                targetView.setVisibility(View.INVISIBLE);
                animator.removeListener(this);
            }
        });
        animator.start();
    }
}
