package com.github.xiaogegechen.module_b.view

import com.github.xiaogegechen.common.base.IBaseView
import com.github.xiaogegechen.module_b.model.Today
import com.github.xiaogegechen.module_b.model.Week
import com.github.xiaogegechen.module_b.model.Year

interface IFragmentBView: IBaseView {

    fun showToady(today: Today)

    fun showWeek(week: Week)

    fun showYear(year: Year)

    fun showBgImage(url: String)

}