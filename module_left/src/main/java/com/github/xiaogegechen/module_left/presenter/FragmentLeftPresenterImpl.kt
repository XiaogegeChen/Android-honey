package com.github.xiaogegechen.module_left.presenter

import com.github.xiaogegechen.module_left.Constants
import com.github.xiaogegechen.module_left.view.IFragmentLeftView

class FragmentLeftPresenterImpl:IFragmentLeftPresenter {

    private var mFragmentLeftView: IFragmentLeftView? = null

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
    }

    override fun gotoSetting() {
    }

    override fun attach(t: IFragmentLeftView) {
        mFragmentLeftView = t
    }

    override fun detach() {
        mFragmentLeftView = null
    }
}