package com.github.xiaogegechen.network

import android.util.Log

internal object LogUtil {

    @JvmStatic
    private var sOpenLog = true

    @JvmStatic
    fun openLog(b: Boolean){
        sOpenLog = b
    }

    fun d(tag: String, message: String){
        if(sOpenLog){
            Log.d(tag, message)
        }
    }
}