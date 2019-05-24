package com.github.xiaogegechen.common.util

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class QueryDataHelper<T> {

    abstract fun queryFromLocal():Observable<T>
    abstract fun queryFromNet():Observable<T>

    fun query(type: Type): Maybe<T>{
        return when (type) {
            Type.NORMAL ->
                Observable.concat(queryFromLocal(), queryFromNet())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .firstElement()
            Type.REFRESH -> Observable.concat(queryFromNet(), queryFromLocal())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .firstElement()
        }
    }

    // 请求类型
    enum class Type{

        // 优先本地，没有再去网络拿
        NORMAL,

        // 优先网络， 没有再去本地拿
        REFRESH
    }

}