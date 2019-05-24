package com.github.xiaogegechen.module_b.view

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.xiaogegechen.common.arouter.ARouterMap.Companion.MODULE_B_FRAGMENT_B_PATH
import com.github.xiaogegechen.common.base.BaseFragment
import com.github.xiaogegechen.common.util.ImageParam
import com.github.xiaogegechen.common.util.ImageUtil
import com.github.xiaogegechen.module_b.R
import com.github.xiaogegechen.module_b.model.Today
import com.github.xiaogegechen.module_b.model.Week
import com.github.xiaogegechen.module_b.model.Year
import com.github.xiaogegechen.module_b.presenter.FragmentBPresenterImpl
import kotlinx.android.synthetic.main.module_b_fragment_b.*
import org.angmarch.views.NiceSpinner

@Route(path = MODULE_B_FRAGMENT_B_PATH)
class FragmentB: BaseFragment(),IFragmentBView {

    private var mFragmentBPresenter: FragmentBPresenterImpl? = null
    private var mBgImage: ImageView? = null
    private lateinit var name: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val spinner: NiceSpinner? = view?.findViewById(R.id.spinner)
        mBgImage = view?.findViewById(R.id.imageBg)
        spinner?.attachDataSource(CONSTELLATION.asList())
        spinner?.addOnItemClickListener { _, _, _, _ ->
            name = spinner.selectedItem as String
            mFragmentBPresenter?.queryToday(name)
            mFragmentBPresenter?.queryWeek(name)
            mFragmentBPresenter?.queryYear(name)
        }

        mFragmentBPresenter?.attach(this)
        mFragmentBPresenter?.queryImage()
        mFragmentBPresenter?.queryToday(name)
        mFragmentBPresenter?.queryWeek(name)
        mFragmentBPresenter?.queryYear(name)

        return view
    }

    override fun initData() {
        mFragmentBPresenter = FragmentBPresenterImpl()
        name = "摩羯座"
    }

    override fun getLayoutId(): Int {
        return R.layout.module_b_fragment_b
    }

    override fun onDestroy() {
        super.onDestroy()
        mFragmentBPresenter?.detach()
    }

    override fun showToady(today: Today) {
        todayTitle.name?.text = today.date
        todayTitle.value?.text = today.name
        todayAll.value?.text = today.all
        todayColor.value?.text = today.color
        todayHealth.value?.text = today.health
        todayLove.value?.text = today.love
        todayMoney.value?.text = today.money
        todayLuckyNum.value?.text = today.number
        todayFriend.value?.text = today.friend
        todayWork.value?.text = today.work
        todaySum.text = today.summary
    }

    @SuppressLint("SetTextI18n")
    override fun showWeek(week: Week) {
        weekTitle.value?.text = "${week.name}本周运势"
        weekTitle.name?.text = week.date
        weekNum.text = "本周是今年的第${week.weekth}周"
        weekLove.text = "${week.love}"
        weekMoney.text = "${week.money}"
        weekWork.text = "${week.work}"
    }

    @SuppressLint("SetTextI18n")
    override fun showYear(year: Year) {
        yearTitle.value?.text = "${year.name}今年运势"
        yearTitle.name?.text = "今年是${year.year}年"
        yearPwd.text = "年度密码：${year.mima?.info}"
        yearSum.text = "年度概述：${year.mima?.text?.get(0)}"
        yearWork.text = "事业运：${year.career?.get(0)}"
        yearLove.text = "感情运：${year.love?.get(0)}"
        yearHealth.text = "健康运：${year.health?.get(0)}"
        yearMoney.text = "财运：${year.finance?.get(0)}"
    }

    override fun showBgImage(url: String) {
        ImageUtil.displayImage(ImageParam.Builder()
            .context(obtainContext())
            .error(ColorDrawable(obtainResources()?.getColor(R.color.design_color_accent)!!))
            .placeholder(ColorDrawable(obtainResources()?.getColor(R.color.design_color_accent)!!))
            .imageView(mBgImage)
            .url(url)
            .build())
    }

    override fun showProgress() {
    }

    override fun showErrorPage() {
    }

    override fun showToast(message: String) {
        Toast.makeText(obtainContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        private val CONSTELLATION = arrayOf("摩羯座","白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","水瓶座","双鱼座")
    }
}