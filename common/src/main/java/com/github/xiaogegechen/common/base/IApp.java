package com.github.xiaogegechen.common.base;

import android.content.Context;

/**
 * 子模块Application 需要实现该接口，从而进行初始化
 */
public interface IApp {
    /**
     * 子模块通过这个方法拿到context
     * @param context applicationContext，子模块需要使用的时候接收
     */
    default void initContext(Context context){}

    /**
     * 初始化greenDao
     */
    default void initGreenDao(){}
}
