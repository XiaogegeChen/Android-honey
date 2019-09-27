package com.github.xiaogegechen.module_left.presenter

import android.content.Context
import android.content.Intent
import com.github.xiaogegechen.common.base.WebActivity
import com.github.xiaogegechen.module_left.Constants
import com.github.xiaogegechen.module_left.view.FragmentLeft
import com.github.xiaogegechen.module_left.view.IFragmentLeftView
import com.github.xiaogegechen.module_left.view.IntroductionActivity
import com.github.xiaogegechen.module_left.view.SettingActivity

class FragmentLeftPresenterImpl:IFragmentLeftPresenter {
    override fun attach(t: IFragmentLeftView?) {
        mFragmentLeftView = t
        mContext = (t as FragmentLeft).context
    }

    private var mFragmentLeftView: IFragmentLeftView? = null
    private var mContext: Context? = null

    override fun queryBgImage() {
        mFragmentLeftView?.showBgImage(Constants.SCENERY_BG_URL)
    }

    override fun queryHeadImage() {
        mFragmentLeftView?.showHeadImage(Constants.HEAD_IMAGE_URL)
    }

    override fun gotoIntroduction() {
        val intent = Intent(mContext, IntroductionActivity::class.java)
        mContext?.startActivity(intent)
    }

    override fun gotoThumb() {
        val intent = Intent(mContext, WebActivity::class.java)
        intent.putExtra(Constants.INTENT_PARAM_NAME, Constants.GITHUB_ACTIVITY_URL)
        mContext?.startActivity(intent)
    }

    override fun gotoShare() {
    }

    override fun gotoBlog() {
        val intent = Intent(mContext, WebActivity::class.java)
        intent.putExtra(Constants.INTENT_PARAM_NAME, Constants.BLOG_ACTIVITY_URL)
        mContext?.startActivity(intent)
    }

    override fun gotoSetting() {
        mContext?.startActivity(Intent(mContext, SettingActivity::class.java))
    }

    override fun detach() {
        mFragmentLeftView = null
    }
}