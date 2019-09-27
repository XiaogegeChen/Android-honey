package com.github.xiaogegechen.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * toast工具类
 */
public class ToastUtil {
    public static void show(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
