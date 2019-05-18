package com.github.xiaogegechen.common.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.github.xiaogegechen.common.base.BaseFragment

abstract class MyFragmentPagerAdapter(private val mFragmentManager: FragmentManager,
                                      private val mFragmentList: MutableList<BaseFragment>?): FragmentPagerAdapter(mFragmentManager) {

    override fun getItem(position: Int): Fragment? {
        return mFragmentList?.get(position)
    }

    override fun getCount(): Int {
        return mFragmentList?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment =  super.instantiateItem(container, position) as Fragment
        mFragmentManager.beginTransaction().show(fragment).commit()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = mFragmentList?.get(position)!!
        mFragmentManager.beginTransaction().hide(fragment).commit()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getTitle()?.get(position)
    }

    // 设置title,如果不是和TabLayout绑定,返回null
    abstract fun getTitle(): List<String>?
}