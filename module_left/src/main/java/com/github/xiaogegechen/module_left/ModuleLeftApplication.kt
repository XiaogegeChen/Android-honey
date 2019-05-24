package com.github.xiaogegechen.module_left

import android.content.Context
import com.github.xiaogegechen.common.base.IApp

class ModuleLeftApplication: IApp {
    override fun initNetwork(): MutableMap<String, String>? {
        return null
    }

    override fun initContext(context: Context?) {
    }
}