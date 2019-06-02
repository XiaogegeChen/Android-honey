package com.github.xiaogegechen.module_a.presenter

import com.github.xiaogegechen.common.event.NotifyDrawerOpenEvent
import com.github.xiaogegechen.module_a.view.IFragmentAView
import org.greenrobot.eventbus.EventBus

class FragmentAPresenterImpl: IFragmentAPresenter {

    private var mFragmentAView: IFragmentAView? = null

    override fun notifyDrawerOpen() {
        EventBus.getDefault().post(NotifyDrawerOpenEvent())
    }

    override fun attach(t: IFragmentAView) {
        mFragmentAView = t
    }

    override fun detach() {
        mFragmentAView = null
    }
}