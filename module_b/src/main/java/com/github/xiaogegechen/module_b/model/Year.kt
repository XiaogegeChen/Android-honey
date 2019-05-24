package com.github.xiaogegechen.module_b.model

import com.google.gson.annotations.SerializedName

class Year{
    var name:String? = null

    var year:String? = null

    var mima: Mima? = null

    var career: MutableList<String>? = null

    var love: MutableList<String>? = null

    var health: MutableList<String>? = null

    var finance: MutableList<String>? = null

    @SerializedName("resultcode")
    var resultCode:String? = null

    @SerializedName("error_code")
    var errorCode:String? = null

    class Mima{
        var info: String? = null
        var text: MutableList<String>? = null
    }

}