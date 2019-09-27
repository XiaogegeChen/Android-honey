package com.github.xiaogegechen.common.base

import android.content.Context

/**
 * 子模块Application 需要实现该接口，从而进行初始化
 */
interface IApp {
    // 子模块通过这个方法拿到context
    fun initContext(context: Context?)
}