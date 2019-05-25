package com.github.xiaogegechen.module_left.presenter

import android.content.Context
import android.content.Intent
import com.github.xiaogegechen.module_left.Constants
import com.github.xiaogegechen.module_left.view.BlogActivity
import com.github.xiaogegechen.module_left.view.FragmentLeft
import com.github.xiaogegechen.module_left.view.IFragmentLeftView

class FragmentLeftPresenterImpl:IFragmentLeftPresenter {

    private var mFragmentLeftView: IFragmentLeftView? = null
    private var mContext: Context? = null

    override fun queryBgImage() {
        mFragmentLeftView?.showBgImage(Constants.SCENERY_BG_URL)
    }

    override fun queryHeadImage() {
        mFragmentLeftView?.showHeadImage(Constants.HEAD_IMAGE_URL)
    }

    override fun gotoIntroduction() {

    }

    override fun gotoThumb() {
    }

    override fun gotoShare() {
    }

    override fun gotoBlog() {
        val intent = Intent(mContext, BlogActivity::class.java)
        mContext?.startActivity(intent)
    }

    override fun gotoSetting() {
    }

    override fun attach(t: IFragmentLeftView) {
        mFragmentLeftView = t
        mContext = (t as FragmentLeft).context
    }

    override fun detach() {
        mFragmentLeftView = null
    }
}