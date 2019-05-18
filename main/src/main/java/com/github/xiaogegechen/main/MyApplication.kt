package com.github.xiaogegechen.main

import android.content.Context
import androidx.multidex.MultiDex
import com.github.xiaogegechen.common.base.App

/**
 * 整个工程的入口application
 */
open class MyApplication: App(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}