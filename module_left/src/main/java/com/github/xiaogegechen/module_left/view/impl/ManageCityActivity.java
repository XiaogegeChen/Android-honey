package com.github.xiaogegechen.module_left.view.impl;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_left.R;
import com.github.xiaogegechen.module_left.adapter.SelectedCityListAdapter;
import com.github.xiaogegechen.module_left.model.SelectedCity;
import com.github.xiaogegechen.module_left.presenter.IManageCityActivityPresenter;
import com.github.xiaogegechen.module_left.presenter.impl.ManageCityActivityPresenterImpl;
import com.github.xiaogegechen.module_left.view.IManageCityActivityView;

import java.util.ArrayList;
import java.util.List;

public class ManageCityActivity extends BaseActivity implements IManageCityActivityView {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private TextView mAddTextView;
    private Group mNothingGroup;

    private SelectedCityListAdapter mSelectedCityListAdapter;
    private List<SelectedCity> mRecyclerViewDataSource;

    private IManageCityActivityPresenter mManageCityActivityPresenter;

    @Override
    public void initView() {
        mAddTextView = findViewById(R.id.module_left_activity_manage_city_add_button);
        mToolbar = findViewById(R.id.module_left_activity_manage_city_toolbar);
        mRecyclerView = findViewById(R.id.module_left_activity_manage_city_list);
        mNothingGroup = findViewById(R.id.module_left_activity_manage_city_nothing_group);
        // imm
        StatusBarUtils.setImmersive(this);
        StatusBarUtils.fillStatusBarByView(this, findViewById(R.id.module_left_activity_manage_city_placeholder));
    }

    @Override
    public void initData() {
        mManageCityActivityPresenter = new ManageCityActivityPresenterImpl();
        mManageCityActivityPresenter.attach(this);
        // 按钮点击
        mAddTextView.setOnClickListener(v -> mManageCityActivityPresenter.gotoSelectCityActivity());
        // toolbar点击监听
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> mManageCityActivityPresenter.finish());
        // mRecyclerView
        mRecyclerViewDataSource = new ArrayList<>();
        mSelectedCityListAdapter = new SelectedCityListAdapter(mRecyclerViewDataSource, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSelectedCityListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 重新查询
        mManageCityActivityPresenter.querySelectedCity();
    }

    @Override
    protected void onDestroy() {
        mManageCityActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            mManageCityActivityPresenter.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

    @Override
    public void showSelectedCity(List<SelectedCity> selectedCityList) {
        mNothingGroup.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerViewDataSource.clear();
        mRecyclerViewDataSource.addAll(selectedCityList);
        mSelectedCityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNothing() {
        mRecyclerView.setVisibility(View.GONE);
        mNothingGroup.setVisibility(View.VISIBLE);
    }
}
