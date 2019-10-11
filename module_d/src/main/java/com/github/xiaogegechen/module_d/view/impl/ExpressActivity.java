package com.github.xiaogegechen.module_d.view.impl;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.model.ExpressJSON;
import com.github.xiaogegechen.module_d.view.IExpressActivityView;

import org.jetbrains.annotations.NotNull;

public class ExpressActivity extends BaseActivity implements IExpressActivityView {

    private ImageView mLeftImageView;
    private ImageView mRightImageView;
    private TextView mTitleTextView;
    private EditText mEditText;

    private ImageView mSupplierLogoImageView;
    private TextView mMessengerNameTextView;
    private TextView mMessengerTelTextView;
    private ImageView mCallPhoneImageView;
    private TextView mSupplierAndNumTextView;

    private RecyclerView mRecyclerView;

    @Override
    public void initView() {
        mLeftImageView = findViewById(R.id.module_d_activity_express_title_left_icon);
        mRightImageView = findViewById(R.id.module_d_activity_express_title_right_icon);
        mTitleTextView = findViewById(R.id.module_d_activity_express_title_text);
        mEditText = findViewById(R.id.module_d_activity_express_title_input);
        mSupplierLogoImageView = findViewById(R.id.module_d_activity_express_supplier_logo_icon);
        mMessengerNameTextView = findViewById(R.id.module_d_activity_express_messenger_name);
        mMessengerTelTextView = findViewById(R.id.module_d_activity_express_messenger_tel);
        mCallPhoneImageView = findViewById(R.id.module_d_activity_express_phone_icon);
        mSupplierAndNumTextView = findViewById(R.id.module_d_activity_express_supplier_and_num);
        mRecyclerView = findViewById(R.id.module_d_activity_express_recycler_view);

        // imm
        StatusBarUtils.setImmersive(this);
        StatusBarUtils.fillStatusBarByView(this, findViewById(R.id.module_d_activity_express_placeholder_view));
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_activity_express;
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
    public void showToast(@NotNull String message) {
        ToastUtil.show(this, message);
    }

    @Override
    public void showInformation(@Nullable ExpressJSON express) {

    }

    @Override
    public void showAnimation() {

    }

    @Override
    public void hideAnimation() {

    }
}
