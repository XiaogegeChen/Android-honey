package com.github.xiaogegechen.main.view

import android.content.Intent
import android.os.Parcelable
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.github.xiaogegechen.common.Constants
import com.github.xiaogegechen.common.adapter.MyFragmentPagerAdapter
import com.github.xiaogegechen.common.arouter.ARouterMap.MAIN_MAIN_ACTIVITY
import com.github.xiaogegechen.common.arouter.ARouterMap.MODULE_LEFT_FRAGMENT_LEFT
import com.github.xiaogegechen.common.base.BaseFragment
import com.github.xiaogegechen.common.base.EventBusActivity
import com.github.xiaogegechen.common.event.MainActivityOnNewIntentEvent
import com.github.xiaogegechen.common.event.NotifyDrawerOpenEvent
import com.github.xiaogegechen.common.util.StatusBarUtils
import com.github.xiaogegechen.common.util.ToastUtil
import com.github.xiaogegechen.main.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 主activity，这个activity是沉浸式的，因此如果哪个子fragment不需要支持沉浸式
 * 那就需要添加一个和状态栏高度一致的view进行占位，比如fragmentA
 *
 * SingleTask模式，会有一些activity返回到这个activity，并通知这个activity的fragment更新UI，需要由这个activity转发
 * 给相应的fragment。也就是onNewIntent()方法只是起到转发的作用，
 */
@Route(path = MAIN_MAIN_ACTIVITY)
class MainActivity : EventBusActivity(), IMainActivityView{

    private lateinit var viewPager: ViewPager

    override fun initView() {
        viewPager = findViewById(R.id.main_activity_view_pager)
        // 沉浸式
        StatusBarUtils.setImmersive(this)
    }

    override fun getStatusBarColor(): Int {
        // 状态栏颜色和主色一致
        return resources.getColor(R.color.main_color_primary)
    }

    override fun initData() {
        val fragmentLeft = ARouter.getInstance().build(MODULE_LEFT_FRAGMENT_LEFT).navigation() as BaseFragment
        val fragmentRight = FragmentRight()
        // viewpager 相关
        val fragmentList: MutableList<BaseFragment> = ArrayList()
        fragmentList.add(fragmentLeft)
        fragmentList.add(fragmentRight)
        viewPager.adapter = object: MyFragmentPagerAdapter(supportFragmentManager, fragmentList){
            override fun getTitle(): List<String>? {
                // 没有标题，返回null
                return null
            }
        }
        viewPager.currentItem = 1
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 来自selectedCityActivity的参数，需要转发给 WeatherFragment
        val dataFromSelectCityActivity = intent?.getParcelableArrayExtra(Constants.INTENT_PARAM_FROM_SELECT_CITY_ACTIVITY)
        if(dataFromSelectCityActivity != null){
            EventBus.getDefault().post(MainActivityOnNewIntentEvent(Constants.INTENT_PARAM_FROM_SELECT_CITY_ACTIVITY, dataFromSelectCityActivity))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.main_activity_main
    }

    override fun isSupportSwipeBack(): Boolean {
        // 不支持侧滑返回
        return false
    }

    override fun showProgress() {
    }

    override fun showErrorPage() {
    }

    override fun showToast(message: String) {
        ToastUtil.show(this, message)
    }

    @Subscribe
    fun onHandleNotifyDrawerOpenEvent(event: NotifyDrawerOpenEvent){
        viewPager.currentItem = 0
    }
}
