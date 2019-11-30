package com.github.xiaogegechen.common.network;

public class CheckHelper {
    /**
     * 校验我的服务端返回结果，并作对应的回调
     * @param checkable 我的服务端返回的结果
     * @param callback 回调
     */
    public static void checkResultFromMyServer(Checkable checkable, Callback callback){
        String errorCode = checkable.errorCode();
        String errorMessage = checkable.errorMessage();
        if("0".equals(errorCode)){
            callback.onSuccess();
        }else{
            callback.onFailure(errorCode, errorMessage);
        }
    }
}
