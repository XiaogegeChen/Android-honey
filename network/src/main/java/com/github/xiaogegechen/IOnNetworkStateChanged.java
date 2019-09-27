package com.github.xiaogegechen;

public interface IOnNetworkStateChanged {
    /**
     * 断开连接时
     */
    void onDisconnect();

    /**
     * 变成数据状态
     */
    void onChangeToMobile();

    /**
     * 变成wifi状态
     */
    void onChangeToWifi();
}
