package com.github.xiaogegechen.network

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit的单例
 */
class RetrofitManager private constructor() {

    private var mRetrofit: Retrofit? = null

    init {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(sConnectTime, TimeUnit.SECONDS)
            .readTimeout (sReadTime, TimeUnit.SECONDS)
            .writeTimeout (sWriteTime, TimeUnit.SECONDS)
            .retryOnConnectionFailure (true)
            .addInterceptor {
                val request = it.request()
                val builer = request.newBuilder()

                // 拿到设置的head
                val headList = request.headers(OKHTTP_HEAD_NAME)
                val oldHttpUrl = request.url()
                if(headList.size > 0){

                    // 移除设置的head
                    builer.removeHeader(OKHTTP_HEAD_NAME)
                    val head = headList[0]
                    var newBaseUrl:HttpUrl? = oldHttpUrl

                    //根据head的值更改baseUrl
                    if (sMap?.get(head) != null) {
                        newBaseUrl = HttpUrl.parse(sMap?.get(head)!!)
                    }

                    // 生成新的url
                    val newUrl = oldHttpUrl.newBuilder()
                        .scheme(newBaseUrl?.scheme()!!)
                        .host(newBaseUrl.host())
                        .port(newBaseUrl.port())
                        .build()

                    LogUtil.d(TAG, "newUrl: $newUrl")
                    return@addInterceptor it.proceed(builer.url(newUrl).build())
                }else{
                    LogUtil.d(TAG, "oldUrl: " + request.url().toString())
                    return@addInterceptor it.proceed(request)
                }
            }
            .build()

        //拿到Retrofit实例
        mRetrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://www.baidu.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofit(): Retrofit{
        return mRetrofit!!
    }

    companion object{

        const val TAG = "RetrofitManager"

        // 动态更改baseurl时用的head的key
        const val OKHTTP_HEAD_NAME = "okhttp_head_name"

        // okhttp连接超时时间
        private var sConnectTime:Long = 6

        // okhttp读取数据超时时间
        private var sReadTime:Long = 6

        // okhttp读取数据超时时间
        private var sWriteTime:Long = 6

        private var sMap : MutableMap<String, String>? = null

        // lazy构造单例
        val instance:RetrofitManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitManager() }

        fun init(map: MutableMap<String, String>, param: TimeoutParam){
            sMap = map

            if(param.writeTime != 0L){
                sWriteTime = param.writeTime
            }

            if(param.readTime != 0L){
                sReadTime = param.readTime
            }

            if(param.connectTime != 0L){
                sConnectTime = param.connectTime
            }
        }
    }
}