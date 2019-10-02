package com.github.xiaogegechen.common.test;

import android.view.View;

import com.github.xiaogegechen.common.base.AppConfig;

/**
 * 点击五次触发view的点击事件
 */
public class FiveClickHelper {
    private static final int GAP = 1000;

    private int count = 0;

    private long startTime;

    public void fiveClick(final View view, final View.OnClickListener listener){
        // 不是debug环境不执行
        if(AppConfig.DEBUG){
            view.setOnClickListener(v -> {
                if(count == 0){
                    startTime = System.currentTimeMillis();
                    count = 1;
                    return;
                }
                long endTime = System.currentTimeMillis();
                if(endTime - startTime <= GAP){
                    count ++;
                    startTime = endTime;
                }else{
                    count = 1;
                    startTime = endTime;
                }
                if(count == 5){
                    listener.onClick(view);
                    count = 0;
                }
            });
        }
    }
}
