package com.github.xiaogegechen.module_d;

import com.github.xiaogegechen.module_d.model.BookListJSON;
import com.github.xiaogegechen.module_d.model.CatalogJSON;
import com.github.xiaogegechen.module_d.model.ExpressJSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    /**
     * 请求网络拿到图书目录，通常这个不怎么会变动，酌情请求（限制次数的）
     * @param key key
     * @param type 返回值类型，固定为"json"
     * @return 图书目录
     */
    @GET("goodbook/catalog")
    Call<CatalogJSON> queryCatalog(@Query("key") String key,
                                   @Query("dtype") String type);

    /**
     * 请求网络拿到单个目录下的全部图书，这个也是通常不怎么刷新，因此也要限制请求次数
     * 一天一次， 请求之后放进数据库，第二天更新数据库。
     * @param key key
     * @param catalogId 目录id
     * @param offset 起始位置，这个根据实际定，先使用0请求一次，拿到总数，如果总数大于
     *               30，那就把它设置为30再请求，以此类推，拿到所有数据
     * @param count 每次请求到的总数，统一设置为30，因为最大就是30了
     * @return 图书列表
     */
    @GET("goodbook/query")
    Call<BookListJSON> queryBookList(@Query("key") String key,
                                     @Query("catalog_id") int catalogId,
                                     @Query("pn") int offset,
                                     @Query("rn") int count);

    /**
     * 请求网络拿到快递信息
     * @param expressNumber 快递单号
     * @return 快递详细信息
     */
    @GET("kdi")
    @Headers("Authorization:APPCODE " + Constants.EXPRESS_APP_CODE)
    Call<ExpressJSON> queryExpress(@Query("no") String expressNumber);
}
