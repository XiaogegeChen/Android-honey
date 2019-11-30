package com.github.xiaogegechen.bing;

import com.github.xiaogegechen.bing.model.json.ImageJSON;
import com.github.xiaogegechen.bing.model.json.ModuleJSON;
import com.github.xiaogegechen.bing.model.json.TopicJSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    /**
     * 请求服务端，拿到bing套图的所有module。我自己的服务器，不限制次数，不需要做缓存
     *
     * @return bing套图的所有module
     */
    @GET("api/bing_pic_module")
    Call<ModuleJSON> queryModule();

    /**
     * 请求服务端，拿到bing套图指定module下的所有topic。自己的服务器，不限制次数，不需要做缓存
     *
     * @param moduleType 指定module的type
     * @return 指定module下的所有topic
     */
    @GET("api/bing_pic_topic")
    Call<TopicJSON> queryTopic(@Query("module_type") String moduleType);

    /**
     * 请求服务端，拿到bing套图指定topic下的所有图片。自己的服务器，不限制次数，不需要做缓存
     *
     * @param moduleType topic所在的module的type
     * @param topicType 指定topic的type
     * @return 指定topic下的所有图片
     */
    @GET("api/bing_pic")
    Call<ImageJSON> queryImage(@Query("module_type") String moduleType,
                                          @Query("topic_type") String topicType);
}
