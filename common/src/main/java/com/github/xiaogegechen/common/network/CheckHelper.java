package com.github.xiaogegechen.common.network;

public class CheckHelper {
    /**
     * 校验我的服务端返回结果，并作对应的回调
     * @param myServerCheckable 我的服务端返回的结果
     * @param callback 回调
     */
    public static void checkResultFromMyServer(MyServerCheckable myServerCheckable, Callback callback){
        String errorCode = myServerCheckable.errorCode();
        String errorMessage = myServerCheckable.errorMessage();
        if("0".equals(errorCode)){
            callback.onSuccess();
        }else{
            callback.onFailure(errorCode, errorMessage);
        }
    }

    /**
     * 校验和风天气的服务端返回结果，并作对应的回调
     * @param hWeatherServerCheckable 和风天气的服务端返回的结果
     * @param callback 回调
     */
    public static void checkResultFromHWeatherServer(HWeatherServerCheckable hWeatherServerCheckable, Callback callback){
        String errorMessage = hWeatherServerCheckable.errorMessage();
        if(HWeatherServerCheckable.OK.equals(errorMessage)){
            callback.onSuccess();
        }else{
            callback.onFailure("0", errorMessage);
        }
    }
}
