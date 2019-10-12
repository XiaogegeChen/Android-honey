package com.github.xiaogegechen.common.util;

import retrofit2.Call;

public class RetrofitHelper {
    /**
     * 取消请求
     * @param call 请求
     */
    public static void cancelCall(Call call){
        if(call != null && !call.isCanceled()){
            call.cancel();
        }
    }

    /**
     * 批量取消请求
     * @param calls 多个网络请求
     */
    public static void cancelCalls(Call... calls){
        for (Call call : calls) {
            cancelCall(call);
        }
    }
}
