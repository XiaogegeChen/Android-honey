package com.github.xiaogegechen.module_d.view.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.dialog.LoadFailedDialog;
import com.github.xiaogegechen.common.dialog.ProgressDialog;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.model.ExpressJSON;
import com.github.xiaogegechen.module_d.presenter.IExpressActivityPresenter;
import com.github.xiaogegechen.module_d.presenter.impl.ExpressActivityPresenterImpl;
import com.github.xiaogegechen.module_d.view.IExpressActivityView;

import org.jetbrains.annotations.NotNull;

public class ExpressActivity extends BaseActivity implements IExpressActivityView {

    private ImageView mLeftImageView;
    private ImageView mRightImageView;
    private ImageView mLineImageView;
    private TextView mTitleTextView;
    private EditText mEditText;

    private ImageView mSupplierLogoImageView;
    private TextView mMessengerNameTextView;
    private TextView mMessengerTelTextView;
    private ImageView mCallPhoneImageView;
    private TextView mSupplierAndNumTextView;

    private RecyclerView mRecyclerView;

    private IExpressActivityPresenter mExpressActivityPresenter;

    // dialog和他们的状态控制位
    private LoadFailedDialog mLoadFailedDialog;
    private ProgressDialog mProgressDialog;
    private boolean mIsLoadFailedDialogAdded = false;
    private boolean mIsProgressDialogAdded = false;

    @Override
    public void initView() {
        mLeftImageView = findViewById(R.id.module_d_activity_express_title_left_icon);
        mRightImageView = findViewById(R.id.module_d_activity_express_title_right_icon);
        mLineImageView = findViewById(R.id.module_d_activity_express_title_line_icon);
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
        mExpressActivityPresenter = new ExpressActivityPresenterImpl();
        mProgressDialog = new ProgressDialog();

        mLeftImageView.setOnClickListener(v -> finish());
        mRightImageView.setOnClickListener(v -> showEditText());
        // 监听输入
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    mRightImageView.setVisibility(View.INVISIBLE);
                }else{
                    mRightImageView.setVisibility(View.VISIBLE);
                }
            }
        });
        // 监听软键盘的搜索键
        mEditText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                mExpressActivityPresenter.queryExpressMessage(mEditText.getText().toString());
            }
            return true;
        });
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

    // 动画时长
    private static final int DURATION_SHORT = 80;
    private static final int DURATION_LONG = 300;

    @Override
    public void showEditText() {
        // 向左的动画，分三步
        //  3           2         1
        //  b b`                 a`a
        // <--__ _______________ __o
        // 1. 第一个动画，右边搜索图标动画到(a`)
        final float originXOfLeftIcon = mLeftImageView.getX();
        final float originXOfRightIcon = mRightImageView.getX();
        final float originXOfLineIcon = mLineImageView.getX();
        final ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(mRightImageView, View.X, mRightImageView.getX(), mLineImageView.getX());
        rightAnimator.setDuration(DURATION_SHORT);
        // 2. 第二个动画，横线图标动画到(b`)
        final ObjectAnimator middleAnimator = ObjectAnimator.ofFloat(mLineImageView, View.X, mLineImageView.getX(), mLeftImageView.getX());
        middleAnimator.setDuration(DURATION_LONG);
        // 3. 第三个动画，右边箭头图标动画到(b)
        final ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(mLeftImageView, View.X, mLeftImageView.getX(), originXOfLeftIcon);
        leftAnimator.setDuration(DURATION_SHORT);
        // 监听器
        rightAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                // 初始化
                // 不可点击
                mLeftImageView.setClickable(false);
                mRightImageView.setClickable(false);
                // 左边返回箭头不可见， 并且瞬移到预定位置(b`)
                if(mLeftImageView.getVisibility() == View.VISIBLE){
                    mLeftImageView.setVisibility(View.INVISIBLE);
                }
                mLeftImageView.setX(mLeftImageView.getRight());
                // 白线不可见
                if(mLineImageView.getVisibility() == View.VISIBLE){
                    mLineImageView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 右边搜索图标隐藏复位，横线出现，开始第二阶段动画
                mRightImageView.setVisibility(View.INVISIBLE);
                mRightImageView.setX(originXOfRightIcon);
                mLineImageView.setVisibility(View.VISIBLE);
                middleAnimator.start();
            }
        });
        middleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 横线隐藏并复位， 左边箭头出现， 开始第三阶段动画
                mLineImageView.setVisibility(View.INVISIBLE);
                mLineImageView.setX(originXOfLineIcon);
                mLeftImageView.setVisibility(View.VISIBLE);
                leftAnimator.start();
            }
        });
        leftAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 全部结束，使能点击事件
                mLeftImageView.setClickable(true);
                mRightImageView.setClickable(true);
                // 右边图标变为X，点击事件为清空输入框内容
                mRightImageView.setImageResource(R.drawable.design_ic_cancel);
                mRightImageView.setOnClickListener(v -> mEditText.setText(null));
                // 左边的点击事件要变为关闭动画和历史记录
                mLeftImageView.setOnClickListener(v -> hideEditText());
                // 隐藏title, 显示editText
                mTitleTextView.setVisibility(View.INVISIBLE);
                mEditText.setVisibility(View.VISIBLE);
                // TODO 打开历史记录
            }
        });
        // 启动动画
        rightAnimator.start();
    }

    @Override
    public void hideEditText() {
        final float originXOfLeftIcon = mLeftImageView.getX();
        final float originXOfRightIcon = mRightImageView.getX();
        final float originXOfLineIcon = mLineImageView.getX();
        // 向右的动画，是上面动画的逆向
        final ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(mLeftImageView, View.X, originXOfLeftIcon, originXOfLeftIcon + mLeftImageView.getWidth());
        leftAnimator.setDuration(DURATION_SHORT);
        final ObjectAnimator middleAnimator = ObjectAnimator.ofFloat(mLineImageView, View.X, originXOfLeftIcon + mLeftImageView.getWidth(), originXOfLineIcon);
        middleAnimator.setDuration(DURATION_LONG);
        final ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(mRightImageView, View.X, originXOfLineIcon, originXOfRightIcon);
        rightAnimator.setDuration(DURATION_SHORT);
        // 动画监听器
        leftAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                // 初始化
                // 右侧图标变为搜索并不可见，瞬移到预定位置
                if(mRightImageView.getVisibility() == View.VISIBLE){
                    mRightImageView.setVisibility(View.INVISIBLE);
                }
                mRightImageView.setImageResource(R.drawable.design_ic_search);
                mRightImageView.setX(originXOfLineIcon);
                // 白线瞬移到预定位置
                mLineImageView.setX(originXOfLeftIcon + mLeftImageView.getWidth());
                // editText不可见
                mEditText.setVisibility(View.INVISIBLE);
                // 不可点击
                mLeftImageView.setClickable(false);
                mRightImageView.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 左边箭头不可见并复位，白线出现，开始中间动画
                mLeftImageView.setVisibility(View.INVISIBLE);
                mLeftImageView.setX(originXOfLeftIcon);
                mLineImageView.setVisibility(View.VISIBLE);
                middleAnimator.start();
            }
        });
        middleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 白线不可见，搜索图标可见，开始右侧动画
                mLineImageView.setVisibility(View.INVISIBLE);
                mRightImageView.setVisibility(View.VISIBLE);
                rightAnimator.start();
            }
        });
        rightAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 全部结束，使能点击事件，左侧箭头可见, 标题可见
                mLeftImageView.setClickable(true);
                mRightImageView.setClickable(true);
                mLeftImageView.setVisibility(View.VISIBLE);
                mTitleTextView.setVisibility(View.VISIBLE);
                // 重定向点击事件
                mLeftImageView.setOnClickListener(v -> finish());
                mRightImageView.setOnClickListener(v -> showEditText());
                // TODO 关闭历史记录
            }
        });
        // 启动动画
        leftAnimator.start();
    }
}
