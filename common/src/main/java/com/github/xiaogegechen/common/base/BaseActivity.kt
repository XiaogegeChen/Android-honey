package com.github.xiaogegechen.common.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity基类
 * 初始化侧滑
 */
abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {

        //初始化侧滑帮助类
        super.onCreate(savedInstanceState)

        // 改变状态栏颜色
        changeStatusBarColor()

        setContentView(getLayoutId())
        initView()
        initData()
    }

    // 获取context
    fun getContext() : Context?{
        return baseContext
    }

    /**
     * 改变状态栏的颜色，-1代表不改变
     * @deprecated 使用StatusBarUtils
     */
    @ColorInt
    open fun getStatusBarColor() : Int{
        return -1
    }

    // 初始化数据
    abstract fun initData()

    abstract fun initView()

    @LayoutRes
    abstract fun getLayoutId() : Int

    // 是否支持滑动返回
    abstract fun isSupportSwipeBack() : Boolean

    // 改变状态栏颜色
    private fun changeStatusBarColor(){
        if(getStatusBarColor() != -1){
            window.statusBarColor = getStatusBarColor()
        }
    }
}