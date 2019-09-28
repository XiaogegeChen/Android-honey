package com.github.xiaogegechen.main.view

import android.util.Log
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.launcher.ARouter
import com.github.xiaogegechen.common.adapter.MyFragmentPagerAdapter
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_A_FRAGMENT_A_PATH
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_B_FRAGMENT_B_PATH
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_C_FRAGMENT_C_PATH
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_D_FRAGMENT_D_PATH
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_LEFT_FRAGMENT_D_LEFT
import com.github.xiaogegechen.common.base.BaseFragment
import com.github.xiaogegechen.common.base.EventBusActivity
import com.github.xiaogegechen.common.event.NotifyDrawerOpenEvent
import com.github.xiaogegechen.common.util.StatusBarUtils
import com.github.xiaogegechen.main.R

import kotlinx.android.synthetic.main.main_activity_main.*
import org.greenrobot.eventbus.Subscribe

/**
 * 主activity，这个activity是沉浸式的，因此如果哪个子fragment不需要支持沉浸式
 * 那就需要添加一个和状态栏高度一致的view进行占位，比如fragmentA
 */
class MainActivity : EventBusActivity(), IMainActivityView{
    override fun initView() {
        // 沉浸式
        StatusBarUtils.setImmersive(this)
    }

    override fun getStatusBarColor(): Int {
        // 状态栏颜色和主色一致
        return resources.getColor(R.color.design_color_accent)
    }

    override fun initData() {

        Log.d(TAG, ARouter.getInstance().build(MODULE_A_FRAGMENT_A_PATH).navigation().toString())

        // 依次拿到五个fragment的实例并存储
        val fragmentA = ARouter.getInstance().build(MODULE_A_FRAGMENT_A_PATH).navigation() as BaseFragment
        val fragmentB = ARouter.getInstance().build(MODULE_B_FRAGMENT_B_PATH).navigation() as BaseFragment
        val fragmentC = ARouter.getInstance().build(MODULE_C_FRAGMENT_C_PATH).navigation() as BaseFragment
        val fragmentD = ARouter.getInstance().build(MODULE_D_FRAGMENT_D_PATH).navigation() as BaseFragment
        val fragmentLeft = ARouter.getInstance().build(MODULE_LEFT_FRAGMENT_D_LEFT).navigation() as BaseFragment

        // drawer替换布局
        supportFragmentManager.beginTransaction().replace(R.id.left, fragmentLeft).commit()

        // viewpager 相关
        val fragmentList: MutableList<BaseFragment> = ArrayList()
        fragmentList.add(fragmentA)
        fragmentList.add(fragmentB)
        fragmentList.add(fragmentC)
        fragmentList.add(fragmentD)

        viewPager.adapter = object: MyFragmentPagerAdapter(supportFragmentManager, fragmentList){
            override fun getTitle(): List<String>? {

                // 没有标题，返回null
                return null
            }
        }

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> navigation.selectedItemId = R.id.bottomA
                    1 -> navigation.selectedItemId = R.id.bottomB
                    2 -> navigation.selectedItemId = R.id.bottomC
                    3 -> navigation.selectedItemId = R.id.bottomD
                }
            }
        })

        // 底部导航监听
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){

                R.id.bottomA -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.bottomB -> {
                    viewPager.currentItem = 1
                    true
                }
                R.id.bottomC -> {
                    viewPager.currentItem = 2
                    true
                }
                R.id.bottomD -> {
                    viewPager.currentItem = 3
                    true
                }
                else -> false
            }
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
    }

    @Subscribe
    fun onHandleNotifyDrawerOpenEvent(event: NotifyDrawerOpenEvent){
        drawer.openDrawer(GravityCompat.START)
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}
