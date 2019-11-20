package com.github.xiaogegechen.module_left.presenter

import com.github.xiaogegechen.common.base.IBasePresenter
import com.github.xiaogegechen.module_left.view.IFragmentLeftView

interface IFragmentLeftPresenter: IBasePresenter<IFragmentLeftView>{
    /**
     * 请求并显示背景图片
     */
    fun queryBgImage()

    /**
     * 请求并显示头像图片
     */
    fun queryHeadImage()

    /**
     * 跳转到app的介绍页面
     */
    fun gotoIntroduction()

    /**
     * 跳转到点赞页面，也就是GitHub主页
     */
    fun gotoThumb()

    /**
     * 分享app给别人
     */
    fun gotoShare()

    /**
     * 跳转到博客页面
     */
    fun gotoBlog()

    /**
     * 跳转到设置页面
     */
    fun gotoSetting()

    /**
     * 跳转到天气页面
     */
    fun gotoWeather()
}