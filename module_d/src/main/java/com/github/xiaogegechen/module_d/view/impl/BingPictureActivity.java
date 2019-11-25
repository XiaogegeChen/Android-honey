package com.github.xiaogegechen.module_d.view.impl;

import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.module_d.R;

public class BingPictureActivity extends BaseActivity {

    @Override
    public void initView() {
        // imm
        StatusBarUtils.setImmersive(this);
    }

    @Override
    public void initData() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.module_d_activity_bing_picture_container, new BingPictureFragment())
                .commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_activity_bing_picture;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }
}
