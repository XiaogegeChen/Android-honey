package com.github.xiaogegechen.module_a.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_A_FRAGMENT_A_PATH
import com.github.xiaogegechen.common.base.BaseFragment
import com.github.xiaogegechen.module_a.R
import com.github.xiaogegechen.module_a.presenter.FragmentAPresenterImpl
import com.google.android.material.tabs.TabLayout

@Route(path = MODULE_A_FRAGMENT_A_PATH)
class FragmentA: BaseFragment(), IFragmentAView {
    override fun initView(view: View) {

    }

    private var mTabLayout: TabLayout? = null
    private var mToolbar: Toolbar? = null

    private var mFragmentAPresenterImpl: FragmentAPresenterImpl? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mFragmentAPresenterImpl?.attach(this)

        mTabLayout = view?.findViewById(R.id.tabLayoutA)
        mToolbar = view?.findViewById(R.id.toolBarA)

        val titleList = obtainResources()?.getStringArray(R.array.module_a_tab_titles)
        titleList?.forEach {
            mTabLayout?.addTab(mTabLayout?.newTab()?.setText(it)!!)
        }

        mToolbar?.setNavigationOnClickListener{
            mFragmentAPresenterImpl?.notifyDrawerOpen()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mFragmentAPresenterImpl?.detach()
    }

    override fun initData() {
        mFragmentAPresenterImpl = FragmentAPresenterImpl()
    }

    override fun getLayoutId(): Int {
        return R.layout.module_a_fragment_a
    }

    override fun showProgress() {
    }

    override fun showErrorPage() {
    }

    override fun showToast(message: String) {
    }

    companion object{
        private const val TAG = "FragmentA"
    }
}