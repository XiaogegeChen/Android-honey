package com.github.xiaogegechen.common.base

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.github.xiaogegechen.common.base.AppConfig.Companion.LOG

open class App: Application() {

    private var mContext: Context? = null

    override fun onCreate() {
        super.onCreate()

        mContext = applicationContext

        // 初始化ARouter
        if(LOG){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this@App)

        // 初始化module
        initModule()
    }

    private fun initModule(){
        // 反射获取所有module的IApp实现类并调用初始化方法
        for(s in MODULE){
            val clazz = Class.forName(s)
            val obj = clazz.newInstance()
            if(obj is IApp){
                // 先执行这个context的赋值
                obj.initContext(mContext)
                obj.initGreenDao()
            }
        }
    }

    companion object{
        //所有module的Application集合
        private val MODULE = arrayOf("com.github.xiaogegechen.module_a.ModuleAApplication",
            "com.github.xiaogegechen.module_b.ModuleBApplication",
            "com.github.xiaogegechen.module_c.ModuleCApplication",
            "com.github.xiaogegechen.module_d.ModuleDApplication",
            "com.github.xiaogegechen.module_left.ModuleLeftApplication"
        )
    }

}