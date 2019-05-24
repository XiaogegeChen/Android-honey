package com.github.xiaogegechen.module_b.model

import com.google.gson.annotations.SerializedName

class Week {

    var name:String? = null

    var weekth:String? = null

    var date:String? = null

    var health:String? = null

    var love:String? = null

    var money:String? = null

    var work:String? = null

    @SerializedName("resultcode")
    var resultCode:String? = null

    @SerializedName("error_code")
    var errorCode:String? = null
}