package com.github.xiaogegechen.common.network;

/**
 * 如果数据来源于和风天气的服务器，需要实现这个接口
 */
public interface HWeatherServerCheckable {

    String OK = "ok";

    // 错误信息，为"ok"时是正确的响应
    String errorMessage();
}
