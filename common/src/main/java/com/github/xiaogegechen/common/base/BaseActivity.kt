package com.github.xiaogegechen.common.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.github.xiaogegechen.common.R

/**
 * Activity基类
 * 初始化侧滑
 */
abstract class BaseActivity : AppCompatActivity(){

    private lateinit var mBGASwipeBackHelper : BGASwipeBackHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        //初始化侧滑帮助类
        initBGASwipeBackHelper()
        super.onCreate(savedInstanceState)

        // 改变状态栏颜色
        changeStatusBarColor()

        setContentView(getLayoutId())

        initData()
    }

    override fun onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (!mBGASwipeBackHelper.isSliding){
            mBGASwipeBackHelper.backward()
        }
    }

    // 获取context
    fun getContext() : Context?{
        return baseContext
    }

    // 改变状态栏的颜色，-1代表不改变
    @ColorInt
    open fun getStatusBarColor() : Int{
        return -1
    }

    // 初始化数据
    abstract fun initData()

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

    // 初始化侧滑
    private fun initBGASwipeBackHelper(){
        mBGASwipeBackHelper = BGASwipeBackHelper(this, object : BGASwipeBackHelper.Delegate{
            override fun onSwipeBackLayoutExecuted() {
                mBGASwipeBackHelper.swipeBackward()
            }

            override fun onSwipeBackLayoutSlide(slideOffset: Float) {
            }

            override fun onSwipeBackLayoutCancel() {
            }

            override fun isSupportSwipeBack(): Boolean {
                return this@BaseActivity.isSupportSwipeBack()
            }
        })

        // 设置滑动返回是否可用。默认值为 true
        mBGASwipeBackHelper.setSwipeBackEnable(true)
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mBGASwipeBackHelper.setIsOnlyTrackingLeftEdge(true)
        // 设置是否是微信滑动返回样式。默认值为 true
        mBGASwipeBackHelper.setIsWeChatStyle(true)
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mBGASwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow)
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mBGASwipeBackHelper.setIsNeedShowShadow(true)
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mBGASwipeBackHelper.setIsShadowAlphaGradient(true)
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mBGASwipeBackHelper.setSwipeBackThreshold(0.3f)
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mBGASwipeBackHelper.setIsNavigationBarOverlap(false)
    }
}