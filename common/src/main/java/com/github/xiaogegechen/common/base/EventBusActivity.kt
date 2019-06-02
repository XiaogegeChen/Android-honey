package com.github.xiaogegechen.common.base

import android.os.Bundle
import org.greenrobot.eventbus.EventBus

abstract class EventBusActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}