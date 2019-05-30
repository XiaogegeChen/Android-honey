package com.github.xiaogegechen.module_left.view

import android.os.Bundle
import android.widget.ListView
import android.widget.Switch
import com.github.xiaogegechen.common.base.BaseActivity
import com.github.xiaogegechen.design.TitleBar
import com.github.xiaogegechen.module_left.R
import com.github.xiaogegechen.module_left.adapter.SettingAdapter
import com.github.xiaogegechen.module_left.model.Setting
import com.github.xiaogegechen.module_left.presenter.SettingActivityPresenterImpl
import java.util.ArrayList

class SettingActivity : BaseActivity(), ISettingActivityView {

    private var mTitleBar: TitleBar? = null
    private var mListView: ListView? = null

    private var mSettingActivityPresenterImpl: SettingActivityPresenterImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mTitleBar = findViewById(R.id.settingTitleBar)
        mListView = findViewById(R.id.settingListView)

        mListView?.adapter = SettingAdapter(getContext()!!, R.layout.module_left_setting_activity_list_item, DATA)

        mTitleBar?.setListener(object: TitleBar.OnArrowClickListener{
            override fun onLeftClick() {
                finish()
            }

            override fun onRightClick() {
            }
        })

        mListView?.setOnItemClickListener{ _, view, position, _ ->
            val setting = DATA[position]
            when(setting.name){
                "应用信息" -> mSettingActivityPresenterImpl?.gotoInformationActivity()
                "清理缓存" -> mSettingActivityPresenterImpl?.clearCache()
                "自动更新" -> {
                    view.findViewById<Switch>(R.id.setSwitch).isChecked = !view.findViewById<Switch>(R.id.setSwitch).isChecked
                    mSettingActivityPresenterImpl?.setAutoRefreshEnable(view.findViewById<Switch>(R.id.setSwitch).isChecked)
                }
                "允许前台推送" ->{
                    view.findViewById<Switch>(R.id.setSwitch).isChecked = !view.findViewById<Switch>(R.id.setSwitch).isChecked
                    mSettingActivityPresenterImpl?.setShowNotificationEnable(view.findViewById<Switch>(R.id.setSwitch).isChecked)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSettingActivityPresenterImpl?.detach()
    }

    override fun showProgress() {
    }

    override fun showErrorPage() {
    }

    override fun showToast(message: String) {
    }

    override fun initData() {
        mSettingActivityPresenterImpl = SettingActivityPresenterImpl()
        mSettingActivityPresenterImpl?.attach(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.module_left_activity_setting
    }

    override fun getStatusBarColor(): Int {
        return resources.getColor(R.color.design_color_accent)
    }

    override fun isSupportSwipeBack(): Boolean {
        return true
    }

    companion object{
        private val DATA: MutableList<Setting>
        init {
            DATA = ArrayList()
            DATA.add(Setting("应用信息", false))
            DATA.add(Setting("清理缓存", false))
            DATA.add(Setting("自动更新", true))
            DATA.add(Setting("允许前台推送", true))
        }
    }

}
