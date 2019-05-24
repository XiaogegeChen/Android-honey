package com.github.xiaogegechen.module_left.presenter

import com.github.xiaogegechen.common.base.IBasePresenter
import com.github.xiaogegechen.module_left.view.IFragmentLeftView

interface IFragmentLeftPresenter: IBasePresenter<IFragmentLeftView>{
    fun queryBgImage()
    fun queryHeadImage()
    fun gotoIntroduction()
    fun gotoThumb()
    fun gotoShare()
    fun gotoBlog()
    fun gotoSetting()
}