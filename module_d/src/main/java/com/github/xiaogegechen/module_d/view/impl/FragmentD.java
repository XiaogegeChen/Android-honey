package com.github.xiaogegechen.module_d.view.impl;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.presenter.impl.FragmentDPresenterImpl;
import com.github.xiaogegechen.module_d.presenter.IFragmentDPresenter;
import com.github.xiaogegechen.module_d.view.IFragmentDView;

import org.jetbrains.annotations.NotNull;

import static com.github.xiaogegechen.common.arouter.ARouterMap.MODULE_D_FRAGMENT_D_PATH;

// NOTE 通过添加一个view占位，然后更改这个view的高度为statusBar的高度
// 就达到了占位的目的

@Route(path = MODULE_D_FRAGMENT_D_PATH)
public class FragmentD extends BaseFragment implements IFragmentDView {
    private static final String TAG = "FragmentD";

    private TextView mTitleNameTextView;
    private TextView mTitleManageTextView;
    private TextView mManageTextView;
    private TextView mTotalTextView;

    private ImageView mVoiceImageView;
    private TextView mVoiceTextView;
    private ImageView mExpressImageView;
    private TextView mExpressTextView;
    private ImageView mBookImageView;
    private TextView mBookTextView;

    // 占位view，因为是沉浸式，防止内容顶上去
    private View mTitlePlaceHolderView;
    private View mPlaceHolderView;

    private IFragmentDPresenter mFragmentDPresenter;

    @Override
    public void initData() {
        // 逻辑绑定
        mFragmentDPresenter = new FragmentDPresenterImpl(obtainActivity());
        mFragmentDPresenter.attach(this);
        // 点击监听
        mManageTextView.setOnClickListener(v -> mFragmentDPresenter.manageTools());
        mVoiceImageView.setOnClickListener(v -> mFragmentDPresenter.gotoVoiceActivity());
        mVoiceTextView.setOnClickListener(v -> mFragmentDPresenter.gotoVoiceActivity());
        mExpressImageView.setOnClickListener(v -> mFragmentDPresenter.gotoExpressActivity());
        mExpressTextView.setOnClickListener(v -> mFragmentDPresenter.gotoExpressActivity());
        mBookImageView.setOnClickListener(v -> mFragmentDPresenter.gotoBookActivity());
        mBookTextView.setOnClickListener(v -> mFragmentDPresenter.gotoBookActivity());

        // title需要在合适的时候再显示，先隐藏
        hideTitle();
        refreshCount();
    }

    @Override
    public void initView(@NotNull View view) {
        mTitleNameTextView = view.findViewById(R.id.module_d_fragment_d_title_name);
        mTitleManageTextView = view.findViewById(R.id.module_d_fragment_d_title_manage);
        mManageTextView = view.findViewById(R.id.module_d_fragment_d_manage);
        mTotalTextView = view.findViewById(R.id.module_d_fragment_d_total);
        mPlaceHolderView = view.findViewById(R.id.module_d_fragment_d_placeholder);
        mTitlePlaceHolderView = view.findViewById(R.id.module_d_fragment_d_title_placeholder);
        mVoiceImageView = view.findViewById(R.id.module_d_fragment_d_first_item_icon_0);
        mVoiceTextView = view.findViewById(R.id.module_d_fragment_d_first_item_text_0);
        mExpressImageView = view.findViewById(R.id.module_d_fragment_d_first_item_icon_1);
        mExpressTextView = view.findViewById(R.id.module_d_fragment_d_first_item_text_1);
        mBookImageView = view.findViewById(R.id.module_d_fragment_d_first_item_icon_2);
        mBookTextView = view.findViewById(R.id.module_d_fragment_d_first_item_text_2);

        // 因为是沉浸式，需要做填充，通过改变placeholder的高度实现
        Activity parent = getActivity();
        if (parent != null) {
            StatusBarUtils.fillStatusBarByView(parent, mPlaceHolderView);
            StatusBarUtils.fillStatusBarByView(parent, mTitlePlaceHolderView);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_d;
    }

    @Override
    public void onDestroy() {
        mFragmentDPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void refreshCount() {
        int count = mFragmentDPresenter.getToolsCount();
        String titleText = mTitleNameTextView.getText().toString();
        String totalText = mTotalTextView.getText().toString();
        mTitleNameTextView.setText(titleText.replace("x", String.valueOf(count)));
        mTotalTextView.setText(totalText.replace("x", String.valueOf(count)));
    }

    @Override
    public void showTitle() {
        mTitleManageTextView.setVisibility(View.VISIBLE);
        mTitleNameTextView.setVisibility(View.VISIBLE);
        mTitlePlaceHolderView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTitle() {
        mTitleManageTextView.setVisibility(View.INVISIBLE);
        mTitleNameTextView.setVisibility(View.INVISIBLE);
        mTitlePlaceHolderView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void showErrorPage() {
    }

    @Override
    public void showToast(@NotNull String message) {
        ToastUtil.show(getActivity(), message);
    }
}
