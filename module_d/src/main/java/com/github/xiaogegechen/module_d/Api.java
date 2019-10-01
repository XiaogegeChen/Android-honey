package com.github.xiaogegechen.module_d;

import com.github.xiaogegechen.module_d.model.CatalogJSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    /**
     * 请求网络拿到图书目录，通常这个不怎么会变动，酌情请求（限制次数的）
     * @param key key
     * @param type 返回值类型，固定为"json"
     * @return 图书目录
     */
    @GET("goodbook/catalog")
    Call<CatalogJSON> queryCatalog(@Query("key") String key, @Query("dtype") String type);
}
