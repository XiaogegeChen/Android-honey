package com.github.xiaogegechen.common.network;

public interface Callback {
    /**
     * 校验结果成功的回调
     */
    void onSuccess();

    /**
     * 校验结果失败的回调
     * @param errorCode 错误码
     * @param errorMessage 错误信息
     */
    void onFailure(String errorCode, String errorMessage);
}
