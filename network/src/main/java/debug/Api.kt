package debug

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface Api {

    @Headers("okhttp_head_name" + ":" + "https://apis.juhe.cn/")
    @GET("goodbook/catalog?key=你申请的key&dtype=json")
    fun testGet(): Observable<Result>
}