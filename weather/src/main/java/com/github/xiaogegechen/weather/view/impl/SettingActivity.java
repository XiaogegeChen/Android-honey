package com.github.xiaogegechen.weather.view.impl;

import android.widget.ListView;
import android.widget.Switch;

import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.design.viewgroup.TitleBar;
import com.github.xiaogegechen.design.viewgroup.impl.OnArrowClickListenerAdapter;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.adapter.SettingAdapter;
import com.github.xiaogegechen.weather.model.Setting;
import com.github.xiaogegechen.weather.presenter.ISettingActivityPresenter;
import com.github.xiaogegechen.weather.presenter.impl.SettingActivityPresenterImpl;
import com.github.xiaogegechen.weather.view.ISettingActivityView;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends BaseActivity implements ISettingActivityView {
    private TitleBar mTitleBar;
    private ListView mListView;

    private ISettingActivityPresenter mSettingActivityPresenter;

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.titleBar);
        mListView = findViewById(R.id.listView);
        // imm
        StatusBarUtils.setImmersive(this);
        StatusBarUtils.fillStatusBarByView(
                this,
                findViewById(R.id.root),
                getResources().getColor(R.color.weather_color_primary)
        );
    }

    @Override
    public void initData() {
        mSettingActivityPresenter = new SettingActivityPresenterImpl();
        mSettingActivityPresenter.attach(this);
        // mTitleBar
        mTitleBar.setListener(new OnArrowClickListenerAdapter(){
            @Override
            public void onLeftClick() {
                finish();
            }
        });
        // mListView
        mListView.setAdapter(new SettingAdapter(
                this,
                R.layout.weather_activity_setting_item,
                getListViewDataSource()
        ));
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            // 点击事件
            switch (position){
                case 0:
                    // 允许背景图切换
                    Switch switchButton = view.findViewById(R.id.switchButton);
                    boolean newState = !switchButton.isChecked();
                    mSettingActivityPresenter.setAllowBgChange(newState);
                    switchButton.setChecked(newState);
                    break;
                default:
                    break;
            }
        });
    }

    private List<Setting> getListViewDataSource(){
        List<Setting> dataSource = new ArrayList<>();
        // 允许背景图切换
        boolean allowBgChange = mSettingActivityPresenter.isAllowBgChange();
        dataSource.add(new Setting(getString(R.string.weather_setting_allow_bg_change), true, allowBgChange));
        return dataSource;
    }

    @Override
    protected void onDestroy() {
        mSettingActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.weather_activity_setting;
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
        ToastUtil.show(message);
    }
}
