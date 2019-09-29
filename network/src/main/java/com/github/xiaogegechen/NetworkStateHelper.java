package com.github.xiaogegechen;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * 网络状态监听
 */
public enum NetworkStateHelper {
    INSTANCE;

    private static final String TAG = "NetworkStateHelper";

    private Context mApplicationContext;
    private NetworkReceiver mNetworkReceiver;

    /**
     * 注册监听器
     * @param listener 监听器
     */
    public void register(IOnNetworkStateChanged listener){
        checkInit ();
        mNetworkReceiver.addListener (listener);
    }

    /**
     * 解注册
     * @param listener 监听器
     */
    public void unRegister(IOnNetworkStateChanged listener){
        checkInit ();
        mNetworkReceiver.removeListener (listener);
    }

    /**
     * 初始化
     * @param applicationContext application级别的context
     */
    public void init(Context applicationContext){
        mApplicationContext = applicationContext;
        mNetworkReceiver = new NetworkReceiver (null);
        // 注册广播
        IntentFilter filter = new IntentFilter ();
        filter.addAction (ConnectivityManager.CONNECTIVITY_ACTION);
        mApplicationContext.registerReceiver (mNetworkReceiver, filter);
    }

    private void checkInit(){
        if (mApplicationContext == null) {
            Log.e (TAG, "please call NetworkStateHelper.INSTANCE.init(Context context) first!");
        }
    }

}
