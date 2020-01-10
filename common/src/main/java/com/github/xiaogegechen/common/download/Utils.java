package com.github.xiaogegechen.common.download;

import java.io.IOException;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Utils {
    /**
     * 拿到远程主机上一个文件的长度
     *
     * @param url url
     * @param okHttpClient 发起请求的okHttpClient
     * @return 文件长度，如果失败返回-1
     */
    public static long getTotalLength(String url, OkHttpClient okHttpClient){
        try{
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody body = response.body();
            if(response.isSuccessful() && body != null){
                long length = body.contentLength();
                body.close();
                return length;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 拿到远程主机上一个文件的长度
     *
     * @param url url
     * @return 文件长度，如果失败返回-1
     */
    public static long getTotalLength(String url){
        return getTotalLength(url, new OkHttpClient());
    }

    /**
     * 保留两位小数
     *
     * @param value value
     * @return 保留两位小数的数字
     */
    public static float round(float value){
        String s = String.format(Locale.ENGLISH, "%.2f", value);
        return Float.parseFloat(s);
    }
}
