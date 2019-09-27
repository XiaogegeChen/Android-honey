package com.github.xiaogegechen.module_left.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import com.github.xiaogegechen.module_left.view.ISettingActivityView
import com.github.xiaogegechen.module_left.view.SettingActivity

class SettingActivityPresenterImpl: ISettingActivityPresenter {
    override fun attach(t: ISettingActivityView?) {
        mSettingActivityView = t
        mContext = mSettingActivityView as SettingActivity
        Log.d(TAG, mContext.toString())
    }

    private var mSettingActivityView: ISettingActivityView? = null
    private var mContext: Context? = null

    override fun gotoInformationActivity() {
        val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + mContext?.packageName))
        Log.d(TAG, intent.toString())
        Log.d(TAG, mContext.toString())
        mContext?.startActivity(intent)
    }

    override fun clearCache() {
    }

    override fun setAutoRefreshEnable(enable: Boolean) {
    }

    override fun setShowNotificationEnable(enable: Boolean) {
    }

    override fun detach() {
        mSettingActivityView = null
    }

    companion object{
        private const val TAG = "SettingImpl"
    }
}