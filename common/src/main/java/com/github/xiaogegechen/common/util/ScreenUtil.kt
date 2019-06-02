package com.github.xiaogegechen.common.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenUtil {

    // 根据手机的分辨率从 dp 的单位 转成为 px(像素)
    fun dp2px(dp:Float): Int{
        return (Resources.getSystem().displayMetrics.density * dp + 0.5).toInt()
    }

    // 根据手机的分辨率从 sp 的单位 转成为 px(像素)
    fun sp2px(sp: Float): Int{
        return (Resources.getSystem().displayMetrics.scaledDensity * sp + 0.5).toInt()
    }

    // 屏幕宽高
    fun getScreenParamsInPixel(context: Context): Array<Int>{
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        return arrayOf(width, height)
    }
}