package com.github.xiaogegechen.common.util;

import okhttp3.OkHttpClient;

public class SingletonHelper {
    private volatile static OkHttpClient sOkHttpClient;

    private SingletonHelper(){}

    public static OkHttpClient getOkHttpClient(){
        if (sOkHttpClient == null) {
            synchronized (SingletonHelper.class){
                if (sOkHttpClient == null) {
                    sOkHttpClient = new OkHttpClient.Builder()
                            .build();
                }
            }
        }
        return sOkHttpClient;
    }
}
