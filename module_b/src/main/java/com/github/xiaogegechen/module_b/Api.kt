package com.github.xiaogegechen.module_b

import com.github.xiaogegechen.module_b.model.Today
import com.github.xiaogegechen.module_b.model.Week
import com.github.xiaogegechen.module_b.model.Year
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    // 聚合今日运势
    @GET("constellation/getAll")
    fun queryToady(@Query("consName") consName: String,
                   @Query("type") type: String,
                   @Query("key") key: String): Call<Today>

    // 聚合本周运势
    @GET("constellation/getAll")
    fun queryWeek(@Query("consName") consName: String,
                   @Query("type") type: String,
                   @Query("key") key: String): Call<Week>

    // 聚合年度运势
    @GET("constellation/getAll")
    fun queryYear(@Query("consName") consName: String,
                  @Query("type") type: String,
                  @Query("key") key: String): Call<Year>
}