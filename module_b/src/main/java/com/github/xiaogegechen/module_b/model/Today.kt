package com.github.xiaogegechen.module_b.model

import com.google.gson.annotations.SerializedName

/**
 * 今日运势bean,需要经过统一处理
 */
class Today: Catchable {
    override fun errorCode(): String {
        return errorCode!!
    }

    override fun toString(): String {
        return "Today(date=$date, name=$name, all=$all, color=$color, health=$health, love=$love, money=$money, number=$number, friend=$friend, summary=$summary, work=$work, resultCode=$resultCode, errorCode=$errorCode)"
    }

    @SerializedName("datetime")
    var date: String? = null

    var name: String? = null

    var all: String? = null

    var color: String? = null

    var health: String? = null

    var love: String? = null

    var money: String? = null

    var number: String? = null

    @SerializedName("QFriend")
    var friend: String? = null

    var summary: String? = null

    var work: String? = null

    @SerializedName("resultcode")
    var resultCode: String? = null

    @SerializedName("error_code")
    var errorCode: String? = null
}