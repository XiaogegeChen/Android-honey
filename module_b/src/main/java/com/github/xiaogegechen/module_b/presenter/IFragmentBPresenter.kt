package com.github.xiaogegechen.module_b.presenter

import com.github.xiaogegechen.common.base.IBasePresenter
import com.github.xiaogegechen.module_b.view.IFragmentBView

interface IFragmentBPresenter:IBasePresenter<IFragmentBView> {

    // 查询今日运势
    fun queryToday(name: String)

    // 查询本周运势
    fun queryWeek(name: String)

    // 查询年度运势
    fun queryYear(name: String)

    // 加载背景图
    fun queryImage()

}