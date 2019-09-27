package com.github.xiaogegechen.common.base

/**
 * mvp业务逻辑接口基类
 */
interface IBasePresenter<T : IBaseView> {

    // 绑定
    fun attach(t : T?)

    // 解绑
    fun detach()
}