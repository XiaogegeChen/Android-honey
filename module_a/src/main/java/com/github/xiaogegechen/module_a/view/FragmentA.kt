package com.github.xiaogegechen.module_a.view

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_A_FRAGMENT_A_PATH
import com.github.xiaogegechen.common.base.BaseFragment
import com.github.xiaogegechen.module_a.R

@Route(path = MODULE_A_FRAGMENT_A_PATH)
class FragmentA: BaseFragment() {

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.module_a_fragment_a
    }
}