package com.github.xiaogegechen.bing.view.impl;

import com.github.xiaogegechen.bing.R;
import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.util.StatusBarUtils;

public class BingActivity extends BaseActivity {

    @Override
    public void initView() {
        // imm
        StatusBarUtils.setImmersive(this);
    }

    @Override
    public void initData() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.bing_activity_bing_picture_container, new BingFragment())
                .commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.bing_activity_bing_picture;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }
}
