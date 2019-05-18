package com.github.xiaogegechen.module_a

import android.content.Context
import com.github.xiaogegechen.common.base.IApp


class ModuleAApplication: IApp {
    override fun initNetwork(): MutableMap<String, String>? {
        return null
    }

    override fun initContext(context: Context?) {
    }

}