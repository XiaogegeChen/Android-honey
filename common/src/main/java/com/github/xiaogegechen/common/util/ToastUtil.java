package com.github.xiaogegechen.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.xiaogegechen.common.R;

/**
 * toast工具类
 */
public class ToastUtil {

    private static final int MARGIN_HORIZONTAL = dp2px(48);
    private static final int POSITION_Y_OFFSET = dp2px(10);

    private static Toast sToast;
    @SuppressLint("StaticFieldLeak")
    private static TextView sTextView;

    public static void init(Context applicationContext){

        // 屏幕高
        int screenWidth = ScreenUtil.INSTANCE.getScreenParamsInPixel(applicationContext)[0];
        // 加载自定义toast视图
        LayoutInflater layoutInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.common_custom_toast, null);
        // 找到toast的文本view
        sTextView = layout.findViewById(android.R.id.message);
        // 为toast的文本view设置layoutParams，为什么是设置toast文本？测试发现toast文本的父视图的layoutParams一致都是
        // wrap_content，更改了也没有用，所以直接设置toast文本view的layoutParams
        int width = screenWidth - MARGIN_HORIZONTAL;
        // 因为toast的文本父节点是CardView，是FrameLayout
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        sTextView.setLayoutParams(layoutParams);
        // 实例化toast
        sToast = new Toast(applicationContext);
        // 时长
        sToast.setDuration(Toast.LENGTH_SHORT);
        // 位置，默认是从statusBar下面开始的
        sToast.setGravity(Gravity.TOP, 0, POSITION_Y_OFFSET);
        sToast.setView(layout);
    }

    /**
     * 显示toast
     *
     * @param message 待显示的文本
     */
    public static void show(String message){
        sTextView.setText(message);
        sToast.show();
    }

    /**
     *  显示toast，不推荐使用的方法，多设计了一个参数
     *
     * @param context context，没有用
     * @param message 待显示的文本
     *
     * @deprecated 多了一个参数context，尽量不使用
     *
     * @see #show(String)
     */
    @Deprecated
    public static void show(Context context, String message){
        show(message);
    }

    private static int dp2px(float dp){
        return ScreenUtil.INSTANCE.dp2px(dp);
    }
}
