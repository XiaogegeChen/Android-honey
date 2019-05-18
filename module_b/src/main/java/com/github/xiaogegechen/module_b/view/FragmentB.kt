package com.github.xiaogegechen.module_b.view

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_B_FRAGMENT_B_PATH
import com.github.xiaogegechen.common.base.BaseFragment
import com.github.xiaogegechen.module_b.R

@Route(path = MODULE_B_FRAGMENT_B_PATH)
class FragmentB: BaseFragment() {

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.module_b_fragment_b
    }
}