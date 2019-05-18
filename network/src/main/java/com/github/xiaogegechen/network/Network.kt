package com.github.xiaogegechen.network

/**
 * network门面类
 */
object Network {

    /**
     * 初始化，初始化baseurl替换表
     * @param headMap baseurl替换表
     */
    fun init(headMap: MutableMap<String, String>){
        init(headMap, TimeoutParam.Builder().readTime(6).writeTime(6).connectTime(6).build())
    }

    /**
     * 初始化，初始化baseurl替换表
     * @param headMap baseurl替换表
     * @param param 超时时间设置
     */
    fun init(headMap: MutableMap<String, String>, param: TimeoutParam){

    }

    /**
     * 设置是否开启日志打印，默认开启
     */
    fun openLog(b: Boolean){
        LogUtil.openLog(b)
    }
}