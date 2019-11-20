package com.github.xiaogegechen.module_a;

import com.github.xiaogegechen.module_a.model.PictureJSON;
import com.github.xiaogegechen.module_a.model.SentenceJSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    /**
     * 从服务器请求图片列表，这个接口是我自己的服务器，没有次数限制
     * @param type 0:你我， 1:风景和美食， 2:表情包
     * @param offset 请求图片起始位置的偏移量。如果这个值大于服务端资源的数量，将不返回url
     * @param count 希望请求到的数量。如果大于服务端可以提供的量，则这个参数不起作用
     * @return 服务端返回的json数据
     */
    @GET("api/picture")
    Call<PictureJSON> queryPicture(@Query("type")String type,
                                   @Query("offset")String offset,
                                   @Query("count")String count);

    /**
     * 从服务器请求短句列表，这个接口是我自己的服务器，没有次数限制
     * @param type 0:爱情类型， 1:唯美类型， 2:搞笑类型
     * @param offset 起始位置偏移量，如果这个值大于服务端资源的数量，将空列表
     * @param count 希望请求到的数量。如果大于服务端可以提供的量，则这个参数不起作用
     * @return 服务端返回的json数据
     */
    @GET("api/sentence")
    Call<SentenceJSON> querySentence(@Query("type")String type,
                                     @Query("offset")String offset,
                                     @Query("count")String count);
}
