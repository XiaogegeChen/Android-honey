package com.github.xiaogegechen.common.network;

/**
 * 用于抽象可以被检查的网络请求结果
 */
public interface Checkable {
    // 错误码
    String errorCode();
    // 错误信息
    String errorMessage();
}
