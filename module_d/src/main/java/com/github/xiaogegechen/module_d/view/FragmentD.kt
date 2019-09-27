package com.github.xiaogegechen.module_d.view

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_D_FRAGMENT_D_PATH
import com.github.xiaogegechen.common.base.BaseFragment
import com.github.xiaogegechen.module_d.R

@Route(path = MODULE_D_FRAGMENT_D_PATH)
class FragmentD: BaseFragment() {
    override fun initView(view: View) {

    }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.module_d_fragment_d
    }
}