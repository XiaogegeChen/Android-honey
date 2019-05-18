package com.github.xiaogegechen.network

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 线程调度管理类,单例
 */
class SchedulerManager private constructor(){

    companion object{

        // lazy 模式构造单例
        val instance: SchedulerManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SchedulerManager()
        }
    }

    /**
     * 调度线程 观察者在主线程,被观察者在io线程
     */
    fun <T>applySchedulers(): ObservableTransformer<T, T>{
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}