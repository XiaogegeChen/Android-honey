package com.github.xiaogegechen.module_left.view

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_LEFT_FRAGMENT_D_LEFT
import com.github.xiaogegechen.common.base.BaseFragment
import com.github.xiaogegechen.module_left.R
import kotlinx.android.synthetic.main.module_left_head_layout.*

@Route(path = MODULE_LEFT_FRAGMENT_D_LEFT)
class FragmentLeft:BaseFragment() {

    override fun initData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.module_left_fragment_left
    }
}