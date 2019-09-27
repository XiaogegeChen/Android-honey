package com.github.xiaogegechen.module_b.presenter;

import android.text.TextUtils;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.MyTextUtils;
import com.github.xiaogegechen.common.util.XmlIOUtil;
import com.github.xiaogegechen.module_b.Api;
import com.github.xiaogegechen.module_b.Constants;
import com.github.xiaogegechen.module_b.model.*;
import com.github.xiaogegechen.module_b.util.RetrofitHelper;
import com.github.xiaogegechen.module_b.view.ConstellationDetailActivity;
import com.github.xiaogegechen.module_b.view.IConstellationDetailActivityView;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConstellationDetailActivityPresenterImpl implements IConstellationDetailActivityPresenter {

    private static final String TAG = "ConsPreImpl";
    private static final String SEPARATOR = "_";

    // 周运势和年运势缓存的有效时长
    private static final long COOKIE_WEEK = 7L * 24L * 60L * 60L * 1000L;
    private static final long COOKIE_YEAR = 366L * 24L * 60L * 60L * 1000L;

    private IConstellationDetailActivityView mConstellationDetailActivityView;
    private ConstellationDetailActivity mConstellationDetailActivity;
    private String mConstellationName;

    // 请求聚合数据的retrofit和请求
    private Retrofit mJuheRetrofit;
    private Call<Today> mJuheTodayCall;
    private Call<Week> mJuheWeekCall;
    private Call<Year> mJuheYearCall;

    public ConstellationDetailActivityPresenterImpl(){
        mJuheRetrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .baseUrl(Constants.JUHE_CONSTELLATION_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void setConstellationName(String constellationName) {
        mConstellationName = constellationName;
    }

    @Override
    public void back() {
        mConstellationDetailActivity.finish();
    }

    @Override
    public void share() {
        // TODO
    }

    @Override
    public void queryToday() {
        LogUtil.d(TAG, "query today");
        // 这个接口会随时更新，因此应该一直使用刷新模式
        mConstellationDetailActivityView.showProgress();
        mJuheTodayCall = mJuheRetrofit.create(Api.class).queryToady(mConstellationName, "today", Constants.JUHE_CONSTELLATION_ACCENT_KEY);
        mJuheTodayCall.enqueue(new Callback<Today>() {
            @Override
            public void onResponse(@NotNull Call<Today> call, @NotNull Response<Today> response) {
                Today today = response.body();
                LogUtil.d(TAG, "query today from net success, the result is : " + today);
                if(today != null){
                    onTodayLoadFromNetSuccess(today);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Today> call, @NotNull Throwable t) {
                LogUtil.d(TAG, "query today from net failed, the error is : " + t);
                consumeException(t);
                // 采用降级策略，从本地拿到数据显示
                String json = XmlIOUtil.INSTANCE.read(getKey(Constants.XML_KEY_TODAY_MODULE_B, mConstellationName), mConstellationDetailActivity);
                try {
                    // 有可能抛异常，在缓存的json不对的时候
                    Today today = new Gson().fromJson(json, Today.class);
                    Head head = new Head(today, Constants.ICON_ID_ANALYSIS);
                    showHeadOnRecyclerView(head);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    // Today从网络加载完成
    private void onTodayLoadFromNetSuccess(Today today){
        mConstellationDetailActivityView.hideProgress();
        // 写入sp
        String json = new Gson().toJson(today);
        XmlIOUtil.INSTANCE.write(getKey(Constants.XML_KEY_TODAY_MODULE_B, mConstellationName), json, mConstellationDetailActivity);
        // 显示在RecyclerView中
        Head head = new Head(today, Constants.ICON_ID_ANALYSIS);
        showHeadOnRecyclerView(head);
    }

    // 在RecyclerView中显示head
    private void showHeadOnRecyclerView(Head head){
        mConstellationDetailActivityView.addToHeadAndShow(head);
    }

    // 显示错误页面
    private void consumeException(Throwable t){
        // 打印异常信息
        t.printStackTrace();
        mConstellationDetailActivityView.hideProgress();
        mConstellationDetailActivityView.showErrorPage();
    }

    @Override
    public void queryWeek() {
        LogUtil.d(TAG, "query week");
        // 这个接口每周刷新一次，因此应该特殊处理
        mConstellationDetailActivityView.showProgress();
        // 从sp中拿到缓存，如果为空说明没有缓存，要请求网络
        String json = XmlIOUtil.INSTANCE.read(getKey(Constants.XML_KEY_WEEK_MODULE_B, mConstellationName), mConstellationDetailActivity);
        if(json == null){
            LogUtil.d(TAG, "queryWeek, but json is null, so try to load from net!");
            queryWeekFromNet();
        }else {
            // 不为空就拿到cookie值，如果超过与当前超过一周，要请求网络
            try{
                Week week = new Gson().fromJson(json, Week.class);
                long cookie = week.getCookie();
                if(System.currentTimeMillis() - cookie >= COOKIE_WEEK){
                    LogUtil.d(TAG, "queryWeek, but cookie is invalid, so try to load from net!");
                    queryWeekFromNet();
                }else{
                    LogUtil.d(TAG, "queryWeek, and cookie is effective, so load from local directly!");
                    // 直接使用缓存
                    Week cache = queryWeekFromLocal();
                    if(cache != null){
                        showWeekOnRecyclerView(cache);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 从网络加载周运势,如果加载失败，就给提示并且使用缓存
    private void queryWeekFromNet(){
        LogUtil.d(TAG, "query week from net");
        mJuheWeekCall = mJuheRetrofit.create(Api.class).queryWeek(mConstellationName, "week", Constants.JUHE_CONSTELLATION_ACCENT_KEY);
        mJuheWeekCall.enqueue(new Callback<Week>() {
            @Override
            public void onResponse(@NotNull Call<Week> call, @NotNull Response<Week> response) {
                Week week = response.body();
                LogUtil.d(TAG, "query week from net success, the result is : " + week);
                if(week != null){
                    onWeekLoadFromNetSuccess(week);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Week> call, @NotNull Throwable t) {
                LogUtil.d(TAG, "query week from net failed, the error is : " + t);
                consumeException(t);
                // 降级策略，使用缓存
                Week week = queryWeekFromLocal();
                if(week != null){
                    showWeekOnRecyclerView(week);
                }
            }
        });
    }

    // 从本地拿到缓存的week
    private Week queryWeekFromLocal(){
        Week result = null;
        try {
            String json = XmlIOUtil.INSTANCE.read(getKey(Constants.XML_KEY_WEEK_MODULE_B, mConstellationName), mConstellationDetailActivity);
            // 有可能抛异常，在缓存的json不对的时候
            result = new Gson().fromJson(json, Week.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 周运势加载完成时的操作
    private void onWeekLoadFromNetSuccess(Week week){
        // 设置cookie并写入sp
        week.setCookie(System.currentTimeMillis());
        String json = new Gson().toJson(week);
        XmlIOUtil.INSTANCE.write(getKey(Constants.XML_KEY_WEEK_MODULE_B,mConstellationName), json, mConstellationDetailActivity);
        // 显示在RecyclerView中
        showWeekOnRecyclerView(week);
    }

    // 在recyclerView中显示week
    private void showWeekOnRecyclerView(Week week){
        mConstellationDetailActivityView.hideProgress();
        List<Object> list = new ArrayList<>();
        checkBodyAndAdd(new Body("本周爱情运势", week.getLove(), new Random().nextFloat(),Constants.ICON_ID_LOVE), list);
        checkBodyAndAdd(new Body("本周事业学业", week.getWork(), new Random().nextFloat(),Constants.ICON_ID_WORK), list);
        checkBodyAndAdd(new Body("本周财富运势", week.getMoney(), new Random().nextFloat(),Constants.ICON_ID_MONEY), list);
        checkBodyAndAdd(new Body("本周健康运势", week.getHealth(), new Random().nextFloat(),Constants.ICON_ID_HEALTH), list);
        mConstellationDetailActivityView.addAndShow(list);
    }

    // 检查是否有相应字段，如果没有那就不显示
    private static void checkBodyAndAdd(Body body, List<Object> bodyList){
        if(!TextUtils.isEmpty(body.getContent())){
            bodyList.add(body);
        }
    }

    // 为了区分不同星座在sp中的缓存，通过加 "_"+urlEncode(consName) 后缀加以区分
    private static String getKey(String base, String consName){
        return base + SEPARATOR + MyTextUtils.urlEncode(consName);
    }

    @Override
    public void queryYear() {
        LogUtil.d(TAG, "query year");
        // 这个接口每年刷新一次，所以要做好缓存，特殊处理
        mConstellationDetailActivityView.showProgress();
        // 从sp中拿到缓存，如果为空说明没有缓存，要请求网络
        String json = XmlIOUtil.INSTANCE.read(getKey(Constants.XML_KEY_YEAR_MODULE_B, mConstellationName), mConstellationDetailActivity);
        if(json == null){
            LogUtil.d(TAG, "queryYear, but json is null, so try to load from net!");
            queryYearFromNet();
        }else {
            // 不为空就拿到cookie值，如果超过与当前超过一年，要请求网络
            try {
                Year year = new Gson().fromJson(json, Year.class);
                long cookie = year.getCookie();
                if(System.currentTimeMillis() - cookie >= COOKIE_YEAR){
                    queryYearFromNet();
                }else{
                    // 直接使用缓存
                    LogUtil.d(TAG, "queryYear, and cookie is effective, so load from local directly!");
                    Year cache = queryYearFromLocal();
                    if(cache != null){
                        showYearOnRecyclerView(cache);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 从网络加载年运势
    private void queryYearFromNet(){
        mJuheYearCall = mJuheRetrofit.create(Api.class).queryYear(mConstellationName, "year", Constants.JUHE_CONSTELLATION_ACCENT_KEY);
        mJuheYearCall.enqueue(new Callback<Year>() {
            @Override
            public void onResponse(@NotNull Call<Year> call, @NotNull Response<Year> response) {
                Year year = response.body();
                LogUtil.d(TAG, "query year from net success, the result is : " + year);
                if(year != null){
                    onYearLoadFromNetSuccess(year);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Year> call, @NotNull Throwable t) {
                LogUtil.d(TAG, "query year from net failed, the error is : " + t);
                consumeException(t);
                // 降级策略，使用缓存
                Year year = queryYearFromLocal();
                if (year != null) {
                    showYearOnRecyclerView(year);
                }
            }
        });
    }

    // 从本地拿到year
    private Year queryYearFromLocal(){
        Year result = null;
        try {
            String json = XmlIOUtil.INSTANCE.read(getKey(Constants.XML_KEY_YEAR_MODULE_B, mConstellationName), mConstellationDetailActivity);
            result = new Gson().fromJson(json, Year.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 年运势加载完成时的操作
    private void onYearLoadFromNetSuccess(Year year){
        // 设置cookie并写入sp
        year.setCookie(System.currentTimeMillis());
        String json = new Gson().toJson(year);
        XmlIOUtil.INSTANCE.write(getKey(Constants.XML_KEY_YEAR_MODULE_B, mConstellationName), json, mConstellationDetailActivity);
        // 显示在RecyclerView中
        showYearOnRecyclerView(year);
    }

    // 在recyclerView中显示year
    private void showYearOnRecyclerView(Year year){
        mConstellationDetailActivityView.hideProgress();
        List<Object> list = new ArrayList<>();
        String summary = "";
        if (year.getMima() != null && year.getMima().getText() != null) {
            summary = year.getMima().getText().get(0);
        }
        String career = "";
        if (year.getCareer() != null) {
            career = year.getCareer().get(0);
        }
        String finance = "";
        if (year.getFinance() != null) {
            finance = year.getFinance().get(0);
        }
        String health = "";
        if (year.getHealth() != null) {
            health = year.getHealth().get(0);
        }
        String love = "";
        if (year.getLove() != null) {
            love = year.getLove().get(0);
        }
        list.add(new Body("年度概述", summary, new Random().nextFloat(),Constants.ICON_ID_LIFE));
        list.add(new Body("年度事业运", career, new Random().nextFloat(),Constants.ICON_ID_WORK));
        list.add(new Body("年度财运", finance, new Random().nextFloat(),Constants.ICON_ID_MONEY));
        list.add(new Body("年度健康运", health, new Random().nextFloat(),Constants.ICON_ID_HEALTH));
        list.add(new Body("年度感情运", love, new Random().nextFloat(),Constants.ICON_ID_LOVE));
        mConstellationDetailActivityView.addAndShow(list);
    }

    @Override
    public void retry() {
        // 刷新
        queryToday();
        queryWeek();
        queryYear();
    }

    @Override
    public void cancel() {
        // do nothing
    }

    @Override
    public void attach(IConstellationDetailActivityView constellationDetailActivityView) {
        mConstellationDetailActivityView = constellationDetailActivityView;
        mConstellationDetailActivity = (ConstellationDetailActivity) mConstellationDetailActivityView;
    }

    @Override
    public void detach() {
        // 断开网络请求，并释放资源
        RetrofitHelper.cancelCalls(mJuheTodayCall, mJuheWeekCall, mJuheYearCall);
        mConstellationDetailActivityView = null;
    }
}
