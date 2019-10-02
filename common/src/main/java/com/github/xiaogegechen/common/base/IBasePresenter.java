package com.github.xiaogegechen.common.base;

/**
 * mvp业务逻辑接口基类
 */
public interface IBasePresenter<T extends IBaseView> {
    /**
     * 绑定视图
     * @param t 视图
     */
    void attach(T t);

    /**
     * 解除绑定
     */
    void detach();

    /**
     * debug，需要时使用
     */
    default void debug(){}
}
