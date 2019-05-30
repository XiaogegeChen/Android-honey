package com.github.xiaogegechen.common.base

import android.app.Application
import android.content.Context
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.alibaba.android.arouter.launcher.ARouter
import com.github.xiaogegechen.common.base.AppConfig.Companion.LOG
import com.github.xiaogegechen.network.Network
import com.github.xiaogegechen.network.TimeoutParam

open class App: Application() {

    private var mContext: Context? = null

    override fun onCreate() {
        super.onCreate()

        mContext = applicationContext

        // 初始化Arouter
        if(LOG){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this@App)

        BGASwipeBackHelper.init(this@App, null)

        // 初始化module
        initModule()
    }

    private fun initModule(){

        // 拿到映射关系
        val map: MutableMap<String, String> = HashMap()

        // 反射获取所有module的IApp实现类并调用初始化方法
        for(s in MODULE){
            val clazz = Class.forName(s)
            val obj = clazz.newInstance()
            if(obj is IApp){

                // 先执行这个context的赋值
                obj.initContext(mContext)

                // 拿到所有的映射关系
                val temp = obj.initNetwork()
                if (temp != null) {
                    map.putAll(temp)
                }
            }
        }

        Network.init(map, TimeoutParam.Builder().connectTime(10).writeTime(10).readTime(10).build())
    }

    companion object{

        //所有module的Application集合
        private val MODULE = arrayOf("com.github.xiaogegechen.module_a.ModuleAApplication",
            "com.github.xiaogegechen.module_b.ModuleBApplication")
    }

}