package com.github.xiaogegechen.module_b.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.github.xiaogegechen.common.util.QueryDataHelper
import com.github.xiaogegechen.common.util.XmlIOUtil
import com.github.xiaogegechen.module_b.Api
import com.github.xiaogegechen.module_b.Constants
import com.github.xiaogegechen.module_b.model.Today
import com.github.xiaogegechen.module_b.model.Week
import com.github.xiaogegechen.module_b.model.Year
import com.github.xiaogegechen.module_b.view.FragmentB
import com.github.xiaogegechen.module_b.view.IFragmentBView
import com.github.xiaogegechen.network.Network
import com.google.gson.Gson
import io.reactivex.Observable

class FragmentBPresenterImpl:IFragmentBPresenter {

    private var mFragmentBView: IFragmentBView? = null

    @SuppressLint("CheckResult")
    override fun queryToday(name: String) {
        QueryTodayHelper(name, (mFragmentBView as FragmentB).obtainContext())
            .query(QueryDataHelper.Type.REFRESH)
            .subscribe({
                if(it!=null && it.errorCode.equals("0") && it.resultCode.equals("200")){

                    // 显示
                    mFragmentBView?.showToady(it)

                    // 保存到本地
                    XmlIOUtil.write(Constants.XML_KEY_TODAY_MODULE_B, Gson().toJson(it), (mFragmentBView as FragmentB).obtainContext()!!)
                }else{
                    mFragmentBView?.showToast(Constants.ERROR)
                }
            }, {
                it.printStackTrace()
                mFragmentBView?.showToast(Constants.ERROR)
            })
    }

    @SuppressLint("CheckResult")
    override fun queryWeek(name: String) {
        QueryWeekHelper(name, (mFragmentBView as FragmentB).obtainContext())
            .query(QueryDataHelper.Type.REFRESH)
            .subscribe({
                if(it!=null && it.errorCode.equals("0") && it.resultCode.equals("200")){

                    // 显示
                    mFragmentBView?.showWeek(it)

                    // 保存到本地
                    XmlIOUtil.write(Constants.XML_KEY_WEEK_MODULE_B, Gson().toJson(it), (mFragmentBView as FragmentB).obtainContext()!!)
                }else{
                    mFragmentBView?.showToast(Constants.ERROR)
                }
            }, {
                it.printStackTrace()
                mFragmentBView?.showToast(Constants.ERROR)
            })
    }

    @SuppressLint("CheckResult")
    override fun queryYear(name: String) {
        QueryYearHelper(name, (mFragmentBView as FragmentB).obtainContext())
            .query(QueryDataHelper.Type.REFRESH)
            .subscribe({
                if(it != null && it.errorCode.equals("0") && it.resultCode.equals("200")){

                    // 显示
                    mFragmentBView?.showYear(it)

                    // 保存到本地
                    XmlIOUtil.write(Constants.XML_KEY_YEAR_MODULE_B, Gson().toJson(it), (mFragmentBView as FragmentB).obtainContext()!!)
                }else{
                    mFragmentBView?.showToast(Constants.ERROR)
                }
            }, {
                it.printStackTrace()
                mFragmentBView?.showToast(Constants.ERROR)
            })
    }

    override fun queryImage() {
        mFragmentBView?.showBgImage(Constants.CONSTELLATION_BG_URL)
    }

    override fun attach(t: IFragmentBView) {
        mFragmentBView = t
    }

    override fun detach() {
        mFragmentBView = null
    }

    // 请求today数据帮助类
    private class QueryTodayHelper(val name: String, val context: Context?): QueryDataHelper<Today>(){

        override fun queryFromLocal(): Observable<Today> {
            return Observable.create {
                val json = XmlIOUtil.read(Constants.XML_KEY_TODAY_MODULE_B, context!!)
                val today = Gson().fromJson<Today>(json, Today::class.java)
                if(today != null){
                    it.onNext(today)
                }
                it.onComplete()
            }
        }

        override fun queryFromNet(): Observable<Today> {
            return Network.query()
                .create(Api::class.java)
                .queryToady(name, "today", Constants.JUHE_CONSTELLATION_ACCENT_KEY)
        }

    }

    // 请求week数据帮助类
    private class QueryWeekHelper(val name: String, val context: Context?): QueryDataHelper<Week>(){

        override fun queryFromLocal(): Observable<Week> {
            return Observable.create {
                val json = XmlIOUtil.read(Constants.XML_KEY_WEEK_MODULE_B, context!!)
                val week = Gson().fromJson<Week>(json, Week::class.java)
                if(week != null){
                    it.onNext(week)
                }
                it.onComplete()
            }
        }

        override fun queryFromNet(): Observable<Week> {
            return Network.query()
                .create(Api::class.java)
                .queryWeek(name, "week", Constants.JUHE_CONSTELLATION_ACCENT_KEY)
        }

    }

    // 请求year数据帮助类
    private class QueryYearHelper(val name: String, val context: Context?): QueryDataHelper<Year>(){

        override fun queryFromLocal(): Observable<Year> {
            return Observable.create {
                val json = XmlIOUtil.read(Constants.XML_KEY_YEAR_MODULE_B, context!!)
                val year = Gson().fromJson<Year>(json, Year::class.java)
                if(year != null){
                    it.onNext(year)
                }
                it.onComplete()
            }
        }

        override fun queryFromNet(): Observable<Year> {
            return Network.query()
                .create(Api::class.java)
                .queryYear(name, "year", Constants.JUHE_CONSTELLATION_ACCENT_KEY)
        }

    }

    companion object{
        private const val TAG = "FragmentBPresenterImpl"
    }
}