package com.github.xiaogegechen.module_b.view;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.xiaogegechen.common.Constants;
import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.common.dialog.ProgressDialog;
import com.github.xiaogegechen.library.CornerButton;
import com.github.xiaogegechen.module_b.R;
import com.github.xiaogegechen.module_b.adapter.ConstellationDetailAdapter;
import com.github.xiaogegechen.module_b.dialog.LoadFailedDialog;
import com.github.xiaogegechen.module_b.model.Params;
import com.github.xiaogegechen.module_b.presenter.ConstellationDetailActivityPresenterImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConstellationDetailActivity extends BaseActivity implements IConstellationDetailActivityView {

    private ConstellationDetailActivityPresenterImpl mConstellationDetailActivityPresenter;
    private ConstellationDetailAdapter mAdapter;

    private ImageView mIcon;
    private CornerButton mShareButton;
    private CornerButton mChangeButton;
    private RecyclerView mRecyclerView;

    private ProgressDialog mProgressDialog;
    private LoadFailedDialog mLoadFailedDialog;

    // 两个dialog的状态
    private boolean mIsProgressDialogAdded;
    private boolean mIsLoadFailedDialogAdded;

    @Override
    public void initView() {
        mIcon = findViewById(R.id.module_b_activity_constellation_detail_icon);
        mShareButton = findViewById(R.id.module_b_activity_constellation_detail_share_button);
        mChangeButton = findViewById(R.id.module_b_activity_constellation_detail_change_button);
        mRecyclerView = findViewById(R.id.module_b_activity_constellation_detail_recycler_view);
    }

    @Override
    public void initData() {
        mAdapter = new ConstellationDetailAdapter(new ArrayList<>(), this);
        mConstellationDetailActivityPresenter = new ConstellationDetailActivityPresenterImpl();
        mConstellationDetailActivityPresenter.attach(this);
        // 参数
        Params params = getIntent().getParcelableExtra(Constants.INTENT_PARAM_NAME);
        String consName = params.getConsName();
        int iconId = params.getIconId();
        mConstellationDetailActivityPresenter.setConstellationName(consName);
        mChangeButton.setText(consName);
        mIcon.setImageResource(iconId);
        mIcon.setOnClickListener(v -> mConstellationDetailActivityPresenter.back());
        mShareButton.setOnClickListener(v -> mConstellationDetailActivityPresenter.share());
        mChangeButton.setOnClickListener(v -> mConstellationDetailActivityPresenter.back());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mProgressDialog = new ProgressDialog();
        mLoadFailedDialog = new LoadFailedDialog();
        mLoadFailedDialog.setOnButtonClickListener(new LoadFailedDialog.OnButtonClickListener() {
            @Override
            public void onExitClick(View view) {
                dismissErrorPage();
                mConstellationDetailActivityPresenter.cancel();
            }

            @Override
            public void onRetryClick(View view) {
                dismissErrorPage();
                mConstellationDetailActivityPresenter.retry();
            }
        });

        // 初始化请求
        mConstellationDetailActivityPresenter.retry();
    }

    // 关闭错误dialog
    private void dismissErrorPage(){
        if(mIsLoadFailedDialogAdded){
            mLoadFailedDialog.dismiss();
            mIsLoadFailedDialogAdded = false;
        }
    }

    @Override
    protected void onDestroy() {
        mConstellationDetailActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_b_activity_constellation_detail;
    }

    @Override
    public int getStatusBarColor() {
        getWindow ()
                .getDecorView ()
                .setSystemUiVisibility (View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        return Color.TRANSPARENT;
    }

    @Override
    public boolean isSupportSwipeBack() {
        // 支持侧滑返回
        return true;
    }

    @Override
    public void overrideAndShow(List<Object> dataList) {
        mAdapter.setData(dataList);
    }

    @Override
    public void addAndShow(List<Object> dataList) {
        mAdapter.addData(dataList);
    }

    @Override
    public void addToHeadAndShow(Object object) {
        mAdapter.addDataToHead(object);
    }

    @Override
    public void hideProgress() {
        if(mIsProgressDialogAdded){
            mProgressDialog.dismiss();
            mIsProgressDialogAdded = false;
        }
    }

    private static final String PROGRESS_DIALOG_TAG = "module_b_progress_dialog_tag";
    private static final String LOAD_CONSTELLATION_FAILED_DIALOG_TAG = "module_b_load_constellation_failed_progress_dialog_tag";

    @Override
    public void showProgress() {
        if(!mIsProgressDialogAdded){
            mProgressDialog.show(getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
            mIsProgressDialogAdded = true;
        }
    }

    @Override
    public void showErrorPage() {
        if(!mIsLoadFailedDialogAdded){
            mLoadFailedDialog.show(getSupportFragmentManager(), LOAD_CONSTELLATION_FAILED_DIALOG_TAG);
            mIsLoadFailedDialogAdded = true;
        }
    }

    @Override
    public void showToast(@NotNull String message) {
        ToastUtil.show(this, message);
    }
}
