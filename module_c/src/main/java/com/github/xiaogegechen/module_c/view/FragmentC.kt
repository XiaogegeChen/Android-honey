package com.github.xiaogegechen.module_c.view

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.xiaogegechen.common.arouter.ARouterMap.MODULE_C_FRAGMENT_C_PATH
import com.github.xiaogegechen.common.test.LifeCycleFragment
import com.github.xiaogegechen.module_c.R

@Route(path = MODULE_C_FRAGMENT_C_PATH)
class FragmentC: LifeCycleFragment() {
    override fun initView(view: View) {

    }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.module_c_fragment_c
    }
}