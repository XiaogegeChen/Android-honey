package com.github.xiaogegechen.module_left.view.impl;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_left.R;
import com.github.xiaogegechen.module_left.presenter.IManageCityActivityPresenter;
import com.github.xiaogegechen.module_left.presenter.impl.ManageCityActivityPresenterImpl;
import com.github.xiaogegechen.module_left.view.IManageCityActivityView;

public class ManageCityActivity extends BaseActivity implements IManageCityActivityView {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private TextView mAddTextView;

    private IManageCityActivityPresenter mManageCityActivityPresenter;

    @Override
    public void initView() {
        mAddTextView = findViewById(R.id.module_left_activity_manage_city_add_button);
        mToolbar = findViewById(R.id.module_left_activity_manage_city_toolbar);
        mRecyclerView = findViewById(R.id.module_left_activity_manage_city_list);
        // imm
        StatusBarUtils.setImmersive(this);
        StatusBarUtils.fillStatusBarByView(this, findViewById(R.id.module_left_activity_manage_city_placeholder));
    }

    @Override
    public void initData() {
        mManageCityActivityPresenter = new ManageCityActivityPresenterImpl();
        mManageCityActivityPresenter.attach(this);

        mAddTextView.setOnClickListener(v -> mManageCityActivityPresenter.gotoSelectCityActivity());
        // toolbar点击监听
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        mManageCityActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_left_activity_manage_city;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void showProgress() {}

    @Override
    public void showErrorPage() {}

    @Override
    public void showToast(String message) {
        ToastUtil.show(this, message);
    }
}
