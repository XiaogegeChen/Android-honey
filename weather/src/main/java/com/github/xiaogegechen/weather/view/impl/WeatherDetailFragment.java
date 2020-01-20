package com.github.xiaogegechen.weather.view.impl;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.util.ImageParam;
import com.github.xiaogegechen.common.util.ImageUtil;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.adapter.AirAdapter;
import com.github.xiaogegechen.weather.adapter.HourlyAdapter;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.Air;
import com.github.xiaogegechen.weather.model.Forecast;
import com.github.xiaogegechen.weather.model.Hourly;
import com.github.xiaogegechen.weather.model.Lifestyle;
import com.github.xiaogegechen.weather.presenter.IWeatherDetailFragmentPresenter;
import com.github.xiaogegechen.weather.presenter.impl.WeatherDetailFragmentPresenterImpl;
import com.github.xiaogegechen.weather.view.IWeatherDetailFragmentView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WeatherDetailFragment extends BaseFragment implements IWeatherDetailFragmentView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mNowTempTextView;
    private TextView mNowWeatherDescriptionTextView;
    private TextView mNowCompareTextView;
    private RecyclerView mHourlyRecyclerView;
    private LinearLayout mForestLinearLayout;
    private RecyclerView mAirRecyclerView;
    private LinearLayout mLifeLinearLayout;

    // mAirRecyclerView 相关
    private List<Air> mAirRecyclerViewDataSource;
    private AirAdapter mAirRecyclerViewAdapter;
    // mHourlyRecyclerView 相关
    private List<Hourly> mHourlyRecyclerViewDataSource;
    private HourlyAdapter mHourlyRecyclerViewAdapter;
    // mForestLinearLayout
    private List<Forecast> mForestLinearLayoutDataSource;
    // mLifeLinearLayout
    private List<Lifestyle> mLifeLinearLayoutDataSource;

    private IWeatherDetailFragmentPresenter mWeatherDetailFragmentPresenter;

    private Activity mActivity;

    // 这个fragment对应的城市
    private CityInfo mCityInfo;

    public WeatherDetailFragment(CityInfo cityInfo) {
        mCityInfo = cityInfo;
    }

    public CityInfo getCityInfo() {
        return mCityInfo;
    }

    @Override
    public void initView(@NotNull View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.module_left_fragment_weather_detail_refresh);
        mNowTempTextView = view.findViewById(R.id.weather_fragment_weather_detail_now_temp);
        mNowWeatherDescriptionTextView = view.findViewById(R.id.weather_fragment_weather_detail_now_des);
        mNowCompareTextView = view.findViewById(R.id.weather_fragment_weather_detail_now_compare);
        mHourlyRecyclerView = view.findViewById(R.id.weather_fragment_weather_detail_hour);
        mForestLinearLayout = view.findViewById(R.id.weather_fragment_weather_detail_forest);
        mAirRecyclerView = view.findViewById(R.id.weather_fragment_weather_detail_air);
        mLifeLinearLayout = view.findViewById(R.id.weather_fragment_weather_detail_life);
    }

    @Override
    public void initData() {
        mWeatherDetailFragmentPresenter = new WeatherDetailFragmentPresenterImpl();
        mWeatherDetailFragmentPresenter.attach(this);
        mActivity = obtainActivity();
        // mSwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeResources(R.color.weather_color_primary, R.color.weather_color_primary_dark);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mWeatherDetailFragmentPresenter.queryWeather(mCityInfo));
        // mAirRecyclerView
        mAirRecyclerViewDataSource = new ArrayList<>();
        mAirRecyclerViewAdapter = new AirAdapter(mAirRecyclerViewDataSource, obtainActivity());
        mAirRecyclerView.setLayoutManager(new LinearLayoutManager(obtainActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAirRecyclerView.setAdapter(mAirRecyclerViewAdapter);
        // mHourlyRecyclerView
        mHourlyRecyclerViewDataSource = new ArrayList<>();
        mHourlyRecyclerViewAdapter = new HourlyAdapter(mHourlyRecyclerViewDataSource, obtainActivity());
        mHourlyRecyclerView.setLayoutManager(new LinearLayoutManager(obtainActivity(), LinearLayoutManager.HORIZONTAL, false));
        mHourlyRecyclerView.setAdapter(mHourlyRecyclerViewAdapter);
        // mForestLinearLayout
        mForestLinearLayoutDataSource = new ArrayList<>();
        // mLifeLinearLayout
        mLifeLinearLayoutDataSource = new ArrayList<>();
        // 进行一次刷新
        mWeatherDetailFragmentPresenter.queryWeather(mCityInfo);
    }

    @Override
    public void onDestroy() {
        mWeatherDetailFragmentPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.weather_fragment_wether_detail;
    }

    @Override
    public void showProgress() {}

    @Override
    public void showErrorPage() {}

    @Override
    public void showToast(String message) {
        ToastUtil.show(mActivity, message);
    }

    @Override
    public void showNow(String tempText, String weatherDescriptionText, String compareText, List<Air> weatherAirList) {
        // 头部
        mNowTempTextView.setText(tempText);
        mNowWeatherDescriptionTextView.setText(weatherDescriptionText);
        Context context = obtainActivity();
        if(context != null){
            mNowCompareTextView.setText(context.getString(R.string.weather_detail_compare, compareText));
        }
        // 底部空气状态部分
        mAirRecyclerViewDataSource.clear();
        mAirRecyclerViewDataSource.addAll(weatherAirList);
        mAirRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHourly(List<Hourly> weatherHourlyList) {
        mHourlyRecyclerViewDataSource.clear();
        mHourlyRecyclerViewDataSource.addAll(weatherHourlyList);
        mHourlyRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showForecast(List<Forecast> weatherForecastList) {
        mForestLinearLayoutDataSource.clear();
        mForestLinearLayoutDataSource.addAll(weatherForecastList);
        mForestLinearLayout.removeAllViews();
        // TODO 优化性能
        if(mForestLinearLayoutDataSource.size() > 0){
            // 逐个加进linerLayout里面
            for (Forecast weatherForecast : mForestLinearLayoutDataSource) {
                View view = LayoutInflater.from(obtainActivity()).inflate(R.layout.weather_fragment_weather_detail_forecast_item, mForestLinearLayout, false);
                ((TextView)(view.findViewById(R.id.weather_fragment_weather_detail_forecast_item_time_desc))).setText(weatherForecast.getTimeDescription());
                ((TextView)(view.findViewById(R.id.weather_fragment_weather_detail_forecast_item_time))).setText(weatherForecast.getTime());
                ((TextView)(view.findViewById(R.id.weather_fragment_weather_detail_forecast_item_condition_desc))).setText(weatherForecast.getConditionDescription());
                ((TextView)(view.findViewById(R.id.weather_fragment_weather_detail_forecast_item_tem_min))).setText(weatherForecast.getTempMin());
                ((TextView)(view.findViewById(R.id.weather_fragment_weather_detail_forecast_item_temp_max))).setText(weatherForecast.getTempMax());
                ImageParam param = new ImageParam.Builder()
                        .context(mActivity)
                        .url(Constants.WEATHER_ICON_URL + weatherForecast.getCode() + ".png")
                        .error(mActivity.getResources().getDrawable(R.drawable.weather_ic_na))
                        .placeholder(mActivity.getResources().getDrawable(R.drawable.weather_ic_na))
                        .imageView(view.findViewById(R.id.weather_fragment_weather_detail_forecast_item_icon))
                        .build();
                ImageUtil.INSTANCE.displayImage(param);
                mForestLinearLayout.addView(view);
            }
        }
    }

    @Override
    public void showLifestyle(List<Lifestyle> weatherLifestyleList) {
        mLifeLinearLayoutDataSource.clear();
        mLifeLinearLayoutDataSource.addAll(weatherLifestyleList);
        mLifeLinearLayout.removeAllViews();
        // TODO 优化性能
        if(mLifeLinearLayoutDataSource.size() > 0){
            // 逐个加进linerLayout里面
            for (Lifestyle weatherLifestyle : mLifeLinearLayoutDataSource) {
                View view = LayoutInflater.from(obtainActivity()).inflate(R.layout.weather_fragment_weather_detail_lifestyle_item, mLifeLinearLayout, false);
                ((TextView)(view.findViewById(R.id.weather_fragment_weather_detail_lifestyle_item_name))).setText(weatherLifestyle.getName());
                ((TextView)(view.findViewById(R.id.weather_fragment_weather_detail_lifestyle_item_value))).setText(weatherLifestyle.getValue());
                ((TextView)(view.findViewById(R.id.weather_fragment_weather_detail_lifestyle_item_detail))).setText(weatherLifestyle.getDetail());
                ((ImageView)(view.findViewById(R.id.weather_fragment_weather_detail_lifestyle_item_icon))).setImageResource(weatherLifestyle.getIconId());
                mLifeLinearLayout.addView(view);
            }
        }
    }

    @Override
    public void showSwipeRefresh() {
        if(!mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing (true);
        }
    }

    @Override
    public void hideSwipeRefresh() {
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing (false);
        }
    }
}
