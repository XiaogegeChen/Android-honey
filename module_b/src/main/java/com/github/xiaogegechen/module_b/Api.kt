package com.github.xiaogegechen.module_b

import com.github.xiaogegechen.module_b.model.Today
import com.github.xiaogegechen.module_b.model.Week
import com.github.xiaogegechen.module_b.model.Year
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    // 聚合今日运势
    @GET("constellation/getAll")
    @Headers(Constants.OKHTTP_HEAD_NAME + ":" +Constants.JUHE_CONSTELLATION_HEAD_KEY)
    fun queryToady(@Query("consName") consName: String,
                   @Query("type") type: String,
                   @Query("key") key: String): Observable<Today>

    // 聚合今日运势
    @GET("constellation/getAll")
    @Headers(Constants.OKHTTP_HEAD_NAME + ":" +Constants.JUHE_CONSTELLATION_HEAD_KEY)
    fun queryWeek(@Query("consName") consName: String,
                   @Query("type") type: String,
                   @Query("key") key: String): Observable<Week>

    // 聚合今日运势
    @GET("constellation/getAll")
    @Headers(Constants.OKHTTP_HEAD_NAME + ":" +Constants.JUHE_CONSTELLATION_HEAD_KEY)
    fun queryYear(@Query("consName") consName: String,
                  @Query("type") type: String,
                  @Query("key") key: String): Observable<Year>


}