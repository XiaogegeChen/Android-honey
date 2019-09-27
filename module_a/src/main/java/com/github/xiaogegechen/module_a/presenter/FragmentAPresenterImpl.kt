package com.github.xiaogegechen.module_a.presenter

import com.github.xiaogegechen.common.event.NotifyDrawerOpenEvent
import com.github.xiaogegechen.module_a.view.IFragmentAView
import org.greenrobot.eventbus.EventBus

class FragmentAPresenterImpl: IFragmentAPresenter {
    override fun attach(t: IFragmentAView?) {
        mFragmentAView = t
    }

    private var mFragmentAView: IFragmentAView? = null

    override fun notifyDrawerOpen() {
        EventBus.getDefault().post(NotifyDrawerOpenEvent())
    }

    override fun detach() {
        mFragmentAView = null
    }
}