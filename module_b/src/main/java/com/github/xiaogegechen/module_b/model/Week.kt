package com.github.xiaogegechen.module_b.model

import com.google.gson.annotations.SerializedName

/**
 * 周运势bean,需要经过统一处理
 */
class Week: Catchable {
    override fun errorCode(): String {
        return errorCode!!
    }

    override fun toString(): String {
        return "Week(name=$name, weekth=$weekth, date=$date, health=$health, love=$love, money=$money, work=$work, cookie=$cookie, resultCode=$resultCode, errorCode=$errorCode)"
    }

    var name:String? = null

    var weekth:String? = null

    var date:String? = null

    var health:String? = null

    var love:String? = null

    var money:String? = null

    var work:String? = null

    /**
     * 上次更新的时间，由于这个接口是每周更新一次，
     * 而接口请求是由次数限制的，因此在一周之内只请求一次
     */
    var cookie:Long = 0

    @SerializedName("resultcode")
    var resultCode:String? = null

    @SerializedName("error_code")
    var errorCode:String? = null
}