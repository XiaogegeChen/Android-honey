package com.github.xiaogegechen.module_d.view.impl;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.base.EventBusActivity;
import com.github.xiaogegechen.common.dialog.LoadFailedDialog;
import com.github.xiaogegechen.common.dialog.ProgressDialog;
import com.github.xiaogegechen.common.test.FiveClickHelper;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.design.viewgroup.TitleBar;
import com.github.xiaogegechen.module_d.Constants;
import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.adapter.BookListAdapter;
import com.github.xiaogegechen.module_d.adapter.CatalogAdapter;
import com.github.xiaogegechen.module_d.event.NotifyBookListRefreshEvent;
import com.github.xiaogegechen.module_d.event.NotifyStartInfoActivityEvent;
import com.github.xiaogegechen.module_d.model.CatalogInfo;
import com.github.xiaogegechen.module_d.model.db.BookInDB;
import com.github.xiaogegechen.module_d.presenter.IBookListActivityPresenter;
import com.github.xiaogegechen.module_d.presenter.impl.BookListActivityPresenterImpl;
import com.github.xiaogegechen.module_d.view.IBookListActivityView;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends EventBusActivity implements IBookListActivityView {

    private static final String TAG = "BookListActivity";

    private static final String PROGRESS_DIALOG_TAG = "book_list_progress_dialog";
    private static final String LOAD_FAILED_DIALOG_TAG = "book_list_load_failed_dialog";

    private TitleBar mTitleBar;
    private RecyclerView mLeftRecyclerView;
    private RecyclerView mRightRecyclerView;

    // dialog
    private LoadFailedDialog mLoadFailedDialog;
    private ProgressDialog mProgressDialog;

    private IBookListActivityPresenter mBookListActivityPresenter;

    // recyclerView相关
    private CatalogAdapter mCatalogAdapter;
    private List<CatalogInfo> mCatalogInfoList;
    private BookListAdapter mBookListAdapter;
    private List<BookInDB> mBookList;

    // LoadFailedDialog的监听器，因为在catalog和bookList加载失败都会弹出LoadFailedDialog
    // 因此应该分开处理
    private LoadFailedDialog.OnButtonClickListener mOnCatalogButtonClickListener;
    private LoadFailedDialog.OnButtonClickListener mOnBookListButtonClickListener;

    // 当前的catalogId，在接收到目录点击通知时更新
    private int mCurrentCatalogId;

    @Override
    public void initData() {
        mBookListActivityPresenter = new BookListActivityPresenterImpl(this);

        mCatalogInfoList = new ArrayList<>();
        mCatalogAdapter = new CatalogAdapter(mCatalogInfoList);
        mBookList = new ArrayList<>();
        mBookListAdapter = new BookListAdapter(mBookList);

        mLoadFailedDialog = new LoadFailedDialog();
        mProgressDialog = new ProgressDialog();

        mOnCatalogButtonClickListener = new LoadFailedDialog.OnButtonClickListener() {
            @Override
            public void onExitClick(View view) {
                showToast(Constants.QUERY_DATA_FAILED);
                hideErrorPage();
            }

            @Override
            public void onRetryClick(View view) {
                mBookListActivityPresenter.retryCatalog();
            }
        };
        mOnBookListButtonClickListener = new LoadFailedDialog.OnButtonClickListener() {
            @Override
            public void onExitClick(View view) {
                // 监听器重置为catalog的监听器
                mLoadFailedDialog.setOnButtonClickListener(mOnCatalogButtonClickListener);
                showToast(Constants.QUERY_DATA_FAILED);
                hideErrorPage();
            }

            @Override
            public void onRetryClick(View view) {
                mBookListActivityPresenter.retryBookList(mCurrentCatalogId);
            }
        };

        // recyclerView初始化
        mBookListActivityPresenter.attach(this);
        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLeftRecyclerView.setAdapter(mCatalogAdapter);

        mRightRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        mRightRecyclerView.setAdapter(mBookListAdapter);

        // 监听器
        mTitleBar.setListener(new TitleBar.OnArrowClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {}
        });
        // 初始化为catalog的监听器，加载bookLis时候替换调，dismiss时候再换回来
        mLoadFailedDialog.setOnButtonClickListener(mOnCatalogButtonClickListener);
        // mProgressDialog 监听返回键
        mProgressDialog.setOnBackPressedListener(() -> {
            LogUtil.d(TAG, "back pressed to dismiss dialog");
            mBookListActivityPresenter.cancel();
        });
        // mLoadFailedDialog 监听返回键
        mLoadFailedDialog.setOnBackPressedListener(() -> {
            showToast(Constants.QUERY_DATA_FAILED);
            hideErrorPage();
        });

        // debug的入口
        new FiveClickHelper().fiveClick(mTitleBar.getTextView(), v -> {
            mBookListActivityPresenter.debug();
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
    public int getStatusBarColor() {
        return getResources().getColor(R.color.design_color_accent);
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
    public void showBookList(List<BookInDB> bookList) {
        mBookList.clear();
        mBookList.addAll(bookList);
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void hideErrorPage() {
        mLoadFailedDialog.dismiss();
    }

    @Subscribe
    public void onNotifyBookListRefreshEvent(NotifyBookListRefreshEvent event){
        // 请求指定目录的图书列表
        int id = event.getCatalogId();
        mCurrentCatalogId = id;
        mLoadFailedDialog.setOnButtonClickListener(mOnBookListButtonClickListener);
        mBookListActivityPresenter.queryBookList(id);
    }

    @Subscribe
    public void onNotifyStartInfoActivityEvent(NotifyStartInfoActivityEvent event){
        mBookListActivityPresenter.gotoInfoActivity(event.getBook(), event.getImageView());
    }

    @Override
    public void showProgress() {
        // 如果需要，先关闭加载失败dialog
        hideErrorPage();
        // 显示加载中dialog
        mProgressDialog.show(getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    @Override
    public void showErrorPage() {
        // 如果需要，先关闭加载中dialog
        hideProgress();
        // 显示加载失败dialog
        mLoadFailedDialog.show(getSupportFragmentManager(), LOAD_FAILED_DIALOG_TAG);
    }

    @Override
    public void showToast(@NotNull String message) {
        ToastUtil.show(this, message);
    }
}
