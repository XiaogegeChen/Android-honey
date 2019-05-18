package com.github.xiaogegechen.network

import io.reactivex.ObservableTransformer
import retrofit2.Retrofit

/**
 * network门面类
 */
object Network {

    /**
     * 请求网络的具体方法
     */
    fun query():Retrofit{
        return RetrofitManager.instance.getRetrofit()
    }

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
        RetrofitManager.init(headMap, param)
    }

    /**
     * 设置是否开启日志打印，默认开启
     */
    fun openLog(b: Boolean){
        LogUtil.openLog(b)
    }

    fun <T>changeThread(): ObservableTransformer<T, T>{
        return SchedulerManager.instance.applySchedulers()
    }

}