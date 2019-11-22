package com.github.xiaogegechen.module_left.view.impl;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_left.R;
import com.github.xiaogegechen.module_left.adapter.WeatherAirAdapter;
import com.github.xiaogegechen.module_left.model.CityInfo;
import com.github.xiaogegechen.module_left.model.WeatherAir;
import com.github.xiaogegechen.module_left.presenter.IWeatherDetailFragmentPresenter;
import com.github.xiaogegechen.module_left.presenter.impl.WeatherDetailFragmentPresenterImpl;
import com.github.xiaogegechen.module_left.view.IWeatherDetailFragmentView;

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
    private List<WeatherAir> mAirRecyclerViewDataSource;
    private WeatherAirAdapter mAirRecyclerViewAdapter;

    private IWeatherDetailFragmentPresenter mWeatherDetailFragmentPresenter;

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
        mNowTempTextView = view.findViewById(R.id.module_left_fragment_weather_detail_now_temp);
        mNowWeatherDescriptionTextView = view.findViewById(R.id.module_left_fragment_weather_detail_now_des);
        mNowCompareTextView = view.findViewById(R.id.module_left_fragment_weather_detail_now_compare);
        mHourlyRecyclerView = view.findViewById(R.id.module_left_fragment_weather_detail_hour);
        mForestLinearLayout = view.findViewById(R.id.module_left_fragment_weather_detail_forest);
        mAirRecyclerView = view.findViewById(R.id.module_left_fragment_weather_detail_air);
        mLifeLinearLayout = view.findViewById(R.id.module_left_fragment_weather_detail_life);
    }

    @Override
    public void initData() {
        mWeatherDetailFragmentPresenter = new WeatherDetailFragmentPresenterImpl();
        mWeatherDetailFragmentPresenter.attach(this);
        // mAirRecyclerView
        mAirRecyclerViewDataSource = new ArrayList<>();
        mAirRecyclerViewAdapter = new WeatherAirAdapter(mAirRecyclerViewDataSource, obtainActivity());
        mAirRecyclerView.setLayoutManager(new LinearLayoutManager(obtainActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAirRecyclerView.setAdapter(mAirRecyclerViewAdapter);
    }

    @Override
    public void onDestroy() {
        mWeatherDetailFragmentPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_left_fragment_wether_detail;
    }

    @Override
    public void showProgress() {}

    @Override
    public void showErrorPage() {}

    @Override
    public void showToast(String message) {
        ToastUtil.show(obtainContext(), message);
    }

    @Override
    public void showNow(String tempText, String weatherDescriptionText, String compareText, List<WeatherAir> weatherAirList) {
        // 头部
        mNowTempTextView.setText(tempText);
        mNowWeatherDescriptionTextView.setText(weatherDescriptionText);
        Context context = obtainActivity();
        if(context != null){
            mNowCompareTextView.setText(context.getString(R.string.module_left_activity_weather_detail_compare, compareText));
        }
        // 底部空气状态部分
        mAirRecyclerViewDataSource.clear();
        mAirRecyclerViewDataSource.addAll(weatherAirList);
        mAirRecyclerViewAdapter.notifyDataSetChanged();
    }
}
