package com.github.xiaogegechen.common.util;

import android.util.Log;

/**
 * log工具
 */
public class LogUtil {

    public static void d(String tag, String message){
        Log.d(tag, message);
    }

    public static void e(String tag, String message){
        Log.e(tag, message);
    }
}
