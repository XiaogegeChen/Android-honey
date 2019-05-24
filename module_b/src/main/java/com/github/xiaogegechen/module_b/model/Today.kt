package com.github.xiaogegechen.module_b.model

import com.google.gson.annotations.SerializedName

/**
 * 今日运势bean
 */
class Today {

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