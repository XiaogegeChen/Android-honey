package com.github.xiaogegechen.common.base

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

abstract class BaseFragment : Fragment(){

    private var mContext: Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        initView(view)
        initData()
        return view
    }

    // 提供一个获取context的方法
    fun obtainContext(): Context?{
        return mContext
    }

    // 提供一个获取Activity的方法
    fun obtainActivity(): FragmentActivity?{
        return mContext as FragmentActivity
    }

    // 提供一个拿到资源文件的方法
    fun obtainResources(): Resources?{
        return mContext?.resources
    }

    // 状态栏文字颜色，返回false为白色，true为黑色
    open fun isStatusBarTextDark(): Boolean{
        return false
    }

    abstract fun initData()

    abstract fun initView(view: View)

    @LayoutRes
    abstract fun getLayoutId(): Int
}