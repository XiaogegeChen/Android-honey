package com.github.xiaogegechen.module_left.presenter

import com.github.xiaogegechen.common.base.IBasePresenter
import com.github.xiaogegechen.module_left.view.ISettingActivityView

interface ISettingActivityPresenter: IBasePresenter<ISettingActivityView> {
    fun gotoInformationActivity()
    fun clearCache()
    fun setAutoRefreshEnable(enable: Boolean)
    fun setShowNotificationEnable(enable: Boolean)
}