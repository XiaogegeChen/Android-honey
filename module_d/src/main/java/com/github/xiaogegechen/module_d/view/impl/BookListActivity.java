package com.github.xiaogegechen.module_d.view.impl;

import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.base.EventBusActivity;
import com.github.xiaogegechen.common.dialog.LoadFailedDialog;
import com.github.xiaogegechen.common.dialog.ProgressDialog;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.design.viewgroup.TitleBar;
import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.adapter.CatalogAdapter;
import com.github.xiaogegechen.module_d.event.NotifyBookListRefreshEvent;
import com.github.xiaogegechen.module_d.model.CatalogInfo;
import com.github.xiaogegechen.module_d.presenter.IBookListActivityPresenter;
import com.github.xiaogegechen.module_d.presenter.impl.BookListActivityPresenterImpl;
import com.github.xiaogegechen.module_d.view.IBookListActivityView;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends EventBusActivity implements IBookListActivityView {

    private static final String TAG = "BookListActivity";

    private static final String PROGRESS_DIALOG_TAG = "module_d_progress_dialog_tag";
    private static final String LOAD_FAILED_DIALOG_TAG = "module_d_load_failed_dialog_tag";

    private TitleBar mTitleBar;
    private RecyclerView mLeftRecyclerView;
    private RecyclerView mRightRecyclerView;

    // dialog和他们的状态控制位
    private LoadFailedDialog mLoadFailedDialog;
    private ProgressDialog mProgressDialog;
    private boolean mIsLoadFailedDialogAdded = false;
    private boolean mIsProgressDialogAdded = false;

    private IBookListActivityPresenter mBookListActivityPresenter;

    private CatalogAdapter mCatalogAdapter;
    private List<CatalogInfo> mCatalogInfoList;

    @Override
    public void initData() {
        mBookListActivityPresenter = new BookListActivityPresenterImpl(this);
        mCatalogInfoList = new ArrayList<>();
        mCatalogAdapter = new CatalogAdapter(mCatalogInfoList);

        mLoadFailedDialog = new LoadFailedDialog();
        mProgressDialog = new ProgressDialog();

        // recyclerView初始化
        mBookListActivityPresenter.attach(this);
        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLeftRecyclerView.setAdapter(mCatalogAdapter);

        // 监听器
        mTitleBar.setListener(new TitleBar.OnArrowClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {}
        });
        mLoadFailedDialog.setOnButtonClickListener(new LoadFailedDialog.OnButtonClickListener() {
            @Override
            public void onExitClick(View view) {
                hideErrorPage();
            }

            @Override
            public void onRetryClick(View view) {
                mBookListActivityPresenter.retry();
            }
        });

        mBookListActivityPresenter.queryCatalog();
    }

    @Override
    public void initView() {
        mLeftRecyclerView = findViewById(R.id.module_d_activity_book_list_recycler_view_left);
        mRightRecyclerView = findViewById(R.id.module_d_activity_book_list_recycler_view_right);
        mTitleBar = findViewById(R.id.module_d_activity_book_list_title_bar);
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_activity_book_list;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onDestroy() {
        mBookListActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void showCatalog(List<CatalogInfo> catalogInfoList) {
        mCatalogInfoList.addAll(catalogInfoList);
        mCatalogAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgress() {
        if(mIsProgressDialogAdded){
            mProgressDialog.dismiss();
            mIsProgressDialogAdded = false;
        }
    }

    @Override
    public void hideErrorPage() {
        if(mIsLoadFailedDialogAdded){
            mLoadFailedDialog.dismiss();
            mIsLoadFailedDialogAdded = false;
        }
    }

    @Subscribe
    public void onNotifyBookListRefreshEvent(NotifyBookListRefreshEvent event){
        int id = event.getCatalogId();
        // TODO 请求图书列表并显示
    }

    @Override
    public void showProgress() {
        // 如果需要，先关闭加载失败dialog
        hideErrorPage();
        // 显示加载中dialog
        if(!mIsProgressDialogAdded){
            mProgressDialog.show(getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
            mIsProgressDialogAdded = true;
        }
    }

    @Override
    public void showErrorPage() {
        // 如果需要，先关闭加载中dialog
        hideProgress();
        // 显示加载失败dialog
        if(!mIsLoadFailedDialogAdded){
            mLoadFailedDialog.show(getSupportFragmentManager(), LOAD_FAILED_DIALOG_TAG);
            mIsLoadFailedDialogAdded = true;
        }
    }

    @Override
    public void showToast(@NotNull String message) {
        ToastUtil.show(this, message);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.d(TAG, "keyCode is :" + keyCode + ", KeyEvent is : " + event);
        return super.onKeyDown(keyCode, event);
    }
}
