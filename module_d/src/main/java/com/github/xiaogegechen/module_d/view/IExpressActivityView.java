package com.github.xiaogegechen.module_d.view;

import androidx.annotation.Nullable;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.module_d.model.ExpressJSON;

public interface IExpressActivityView extends IBaseView {
    /**
     * 显示查询到的信息
     * @param express 从网络拿到的数据
     */
    void showInformation(@Nullable ExpressJSON express);

    /**
     * 显示动画
     */
    void showAnimation();

    /**
     * 隐藏动画
     */
    void hideAnimation();
}
