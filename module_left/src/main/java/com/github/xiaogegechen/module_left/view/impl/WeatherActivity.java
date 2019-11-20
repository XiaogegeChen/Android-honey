package com.github.xiaogegechen.module_left.view.impl;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_left.R;
import com.github.xiaogegechen.module_left.presenter.IWeatherActivityPresenter;
import com.github.xiaogegechen.module_left.presenter.impl.WeatherActivityPresenterImpl;
import com.github.xiaogegechen.module_left.view.IWeatherActivityView;

/**
 * singleTask 模式
 */
public class WeatherActivity extends BaseActivity implements IWeatherActivityView {

    private ImageView mAddImageView;
    private TextView mCityNameTextView;
    private TextView mRefreshTimeTextView;
    private ViewPager mViewPager;

    private IWeatherActivityPresenter mWeatherActivityPresenter;

    @Override
    public void initView() {
        mAddImageView = findViewById(R.id.module_left_activity_weather_add);
        mCityNameTextView = findViewById(R.id.module_left_activity_weather_city_name);
        mRefreshTimeTextView = findViewById(R.id.module_left_activity_weather_refresh_time);
        mViewPager = findViewById(R.id.module_left_activity_weather_content);

        // imm
        StatusBarUtils.setImmersive(this);
        StatusBarUtils.fillStatusBarByView(this, findViewById(R.id.module_left_activity_weather_placeholder_view));
    }

    @Override
    public void initData() {
        mWeatherActivityPresenter = new WeatherActivityPresenterImpl();
        mWeatherActivityPresenter.attach(this);
        mAddImageView.setOnClickListener(v -> mWeatherActivityPresenter.gotoManageCityActivity());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        mWeatherActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_left_activity_weather;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showToast(String message) {
        ToastUtil.show(this, message);
    }
}
