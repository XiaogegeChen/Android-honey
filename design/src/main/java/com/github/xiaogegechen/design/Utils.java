package com.github.xiaogegechen.design;

import android.content.res.Resources;

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

}
