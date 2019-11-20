package com.github.xiaogegechen.module_a.presenter

import com.github.xiaogegechen.common.base.IBasePresenter
import com.github.xiaogegechen.module_a.view.IFragmentAView

interface IFragmentAPresenter: IBasePresenter<IFragmentAView> {
    /**
     * 通知MainActivity打开左边的页面
     */
    fun notifyDrawerOpen()
}