package com.github.xiaogegechen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class NetworkReceiver extends BroadcastReceiver {

    private static final int WIFI = 0;
    private static final int MOBILE = 1;
    private static final int NONE = 2;

    @IntDef(value = {
            WIFI,
            MOBILE,
            NONE
    })
    @Retention (RetentionPolicy.SOURCE)
    public @interface NetState{}

    private List<IOnNetworkStateChanged> mListeners;

    public NetworkReceiver(List<IOnNetworkStateChanged> listeners) {
        if (listeners == null) {
            mListeners = new ArrayList<> ();
        }else{
            mListeners = listeners;
        }
    }

    public synchronized void addListeners(@NonNull List<IOnNetworkStateChanged> listeners) {
        mListeners.addAll (listeners);
    }

    public synchronized void addListener(@NonNull IOnNetworkStateChanged listener){
        mListeners.add (listener);
    }

    public synchronized void removeListener(@NonNull IOnNetworkStateChanged listene){
        mListeners.remove (listene);
    }

    public synchronized void removeListeners(@NonNull List<IOnNetworkStateChanged> listeners){
        mListeners.removeAll (listeners);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 遍历通知所有注册者
        if(mListeners != null && mListeners.size () > 0){
            NetworkInfo info = intent.getParcelableExtra (ConnectivityManager.EXTRA_NETWORK_INFO);
            int state = getNetState (info);
            switch (state){
                case WIFI:
                    for (IOnNetworkStateChanged listener : mListeners) {
                        listener.onChangeToWifi ();
                    }
                    break;
                case MOBILE:
                    for (IOnNetworkStateChanged listener : mListeners) {
                        listener.onChangeToMobile ();
                    }
                    break;
                case NONE:
                    for (IOnNetworkStateChanged listener : mListeners) {
                        listener.onDisconnect ();
                    }
                    break;
            }
        }
    }

    // 判断连接状态
    private @NetState int getNetState(NetworkInfo info){
        if(info.getState () == NetworkInfo.State.CONNECTED){
            // wifi 还是 数据
            if(info.getType () == ConnectivityManager.TYPE_WIFI){
                return WIFI;
            } else if(info.getType () == ConnectivityManager.TYPE_MOBILE){
                return MOBILE;
            }
        }
        return NONE;
    }
}
