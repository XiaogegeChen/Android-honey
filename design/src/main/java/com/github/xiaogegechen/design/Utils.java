package com.github.xiaogegechen.design;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.Px;

public class Utils {

    /**
     * dp转化为px
     *
     * @param dp dp
     * @return px
     */
    public static int dp2px(float dp){
        return (int)(Resources.getSystem().getDisplayMetrics().density * dp + 0.5);
    }

    /**
     * 拿到通用的marginTop值
     *
     * @param context context
     * @return px
     */
    @Px
    public static int getCommonMarginTopInPx(Context context){
        if (context != null) {
            return context.getResources().getDimensionPixelSize(R.dimen.design_margin_top);
        }
        return 0;
    }

}
