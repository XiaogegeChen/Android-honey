package com.github.xiaogegechen.common.util;

import android.util.Log;

import com.github.xiaogegechen.common.base.AppConfig;

/**
 * log工具
 */
public class LogUtil {

    public static void d(String tag, String message){
        if(AppConfig.LOG){
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message){
        if(AppConfig.LOG){
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message){
        if(AppConfig.LOG){
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message){
        if(AppConfig.LOG){
            Log.e(tag, message);
        }
    }
}
