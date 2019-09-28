package com.github.xiaogegechen.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 * 状态栏工具类
 */
public class StatusBarUtils {
    /**
     * 获取状态栏高度
     * @param applicationContext 全局context，从中拿到资源文件
     * @return 状态栏高度，如果获取失败返回0
     */
    public static int getHeight(Context applicationContext){
        int height = 0;
        try{
            int resId = applicationContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if(resId > 0){
                height = applicationContext.getResources().getDimensionPixelSize(resId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return height;
    }

    /**
     * 设置状态栏的背景颜色
     * @param window window，可以是activity或者dialog的window
     * @param color 背景颜色
     */
    public static void setColor(Window window, @ColorInt int color){
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(color);
    }

    /**
     * 设置状态栏的背景颜色
     * @param activity activity
     * @param color 背景颜色
     */
    public static void setColor(@NonNull Activity activity, @ColorInt int color){
        setColor(activity.getWindow(), color);
    }

    /**
     * 设置状态栏文字颜色
     * @param window window，可以是activity或者dialog的window
     * @param isDark 是否是黑色
     */
    public static void setTextDark(Window window, boolean isDark){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if(isDark){
                decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else{
                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 设置状态栏文字颜色
     * @param activity activity
     * @param isDark 是否是黑色
     */
    public static void setTextDark(@NonNull Activity activity, boolean isDark){
        setTextDark(activity.getWindow(), isDark);
    }

    /**
     * 设置为沉浸式，前提是主题为NoActionBar，这样view才能顶到状态栏
     * @param window window，可以是activity或者dialog的window
     */
    public static void setImmersive(Window window){
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 设置为沉浸式，前提是主题为NoActionBar，这样view才能顶到状态栏
     * @param activity activity
     */
    public static void setImmersive(@NonNull Activity activity){
        setImmersive(activity.getWindow());
    }

}
