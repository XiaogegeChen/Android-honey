package com.github.xiaogegechen.common.base;

/**
 * mvp视图基类
 */
public interface IBaseView {
    /**
     * 进度页
     */
    void showProgress();

    /**
     * 加载错误页
     */
    void showErrorPage();

    /**
     * 显示toast
     * @param message message
     */
    void showToast(String message);
}
