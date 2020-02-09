package com.github.xiaogegechen.common.network;

/**
 * 用于抽象可以被检查的网络请求结果，如果数据来自我自己的服务器需要实现这个接口
 */
public interface MyServerCheckable {
    // 错误码
    String errorCode();
    // 错误信息
    String errorMessage();
}
