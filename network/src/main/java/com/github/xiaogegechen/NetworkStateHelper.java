package com.github.xiaogegechen;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

public enum NetworkStateHelper {
    INSTANCE;

    private static final String TAG = "NetworkStateHelper";

    private Context mContext;
    private NetworkReceiver mNetworkReceiver;

    public void register(IOnNetworkStateChanged listener){
        checkInit ();
        mNetworkReceiver.addListener (listener);
    }

    public void unRegister(IOnNetworkStateChanged listener){
        checkInit ();
        mNetworkReceiver.removeListener (listener);
    }

    public void init(Context context){
        mContext = context;
        mNetworkReceiver = new NetworkReceiver (null);
        // 注册广播
        IntentFilter filter = new IntentFilter ();
        filter.addAction (ConnectivityManager.CONNECTIVITY_ACTION);
        mContext.registerReceiver (mNetworkReceiver, filter);
    }

    private void checkInit(){
        if (mContext == null) {
            Log.e (TAG, "please call NetworkStateHelper.INSTANCE.init(Context context) first!");
        }
    }

}
