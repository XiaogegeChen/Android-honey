package com.github.xiaogegechen.module_left.view

import com.github.xiaogegechen.common.base.BaseActivity
import com.github.xiaogegechen.design.TitleBar
import com.github.xiaogegechen.module_left.R

class IntroductionActivity: BaseActivity() {

    private var mTitleBar: TitleBar? = null

    override fun initData() {
        mTitleBar = findViewById(R.id.title)
        mTitleBar?.setListener(object : TitleBar.OnArrowClickListener{
            override fun onLeftClick() {
                finish()
            }

            override fun onRightClick() {
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.module_left_activity_introduction
    }

    override fun isSupportSwipeBack(): Boolean {
        return true
    }
}