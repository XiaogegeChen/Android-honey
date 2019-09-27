package com.github.xiaogegechen.module_b.model

import com.google.gson.annotations.SerializedName

/**
 * 年运势bean,需要经过统一处理
 */
class Year: Catchable{
    override fun errorCode(): String {
        return errorCode!!
    }

    override fun toString(): String {
        return "Year(name=$name, year=$year, mima=$mima, career=$career, love=$love, health=$health, finance=$finance, cookie=$cookie, resultCode=$resultCode, errorCode=$errorCode)"
    }

    var name:String? = null

    var year:String? = null

    var mima: Mima? = null

    var career: MutableList<String>? = null

    var love: MutableList<String>? = null

    var health: MutableList<String>? = null

    var finance: MutableList<String>? = null

    /**
     * 上次更新的时间，由于这个接口是每周更新一次，
     * 而接口请求是由次数限制的，因此在一周之内只请求一次
     */
    var cookie:Long = 0

    @SerializedName("resultcode")
    var resultCode:String? = null

    @SerializedName("error_code")
    var errorCode:String? = null

    class Mima{
        var info: String? = null
        var text: MutableList<String>? = null
        override fun toString(): String {
            return "Mima(info=$info, text=$text)"
        }
    }

}