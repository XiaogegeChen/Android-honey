package com.github.xiaogegechen.module_d.view.impl;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.adapter.BingPictureModuleListAdapter;
import com.github.xiaogegechen.module_d.model.BingPictureModule;
import com.github.xiaogegechen.module_d.presenter.IBingPictureFragmentPresenter;
import com.github.xiaogegechen.module_d.presenter.impl.BingPictureFragmentPresenterImpl;
import com.github.xiaogegechen.module_d.view.IBingPictureFragmentView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BingPictureFragment extends BaseFragment implements IBingPictureFragmentView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mModuleRecyclerView;
    private ViewGroup mErrorPageViewGroup;
    private Activity mActivity;
    // mModuleRecyclerView相关
    private List<BingPictureModule> mModuleRecyclerViewDataSource;
    private BingPictureModuleListAdapter mModuleRecyclerViewAdapter;

    private IBingPictureFragmentPresenter mBingPictureFragmentPresenter;

    @Override
    public void initView(@NotNull View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.module_d_fragment_bing_picture_refresh);
        mModuleRecyclerView = view.findViewById(R.id.module_d_fragment_bing_picture_content_list);
        mErrorPageViewGroup = view.findViewById(R.id.module_d_fragment_bing_picture_error);
        mActivity = obtainActivity();
        // imm
        if(mActivity != null){
            StatusBarUtils.fillStatusBarByView(mActivity, view.findViewById(R.id.module_d_fragment_bing_picture_placeholder));
        }
    }

    @Override
    public void initData() {
        mBingPictureFragmentPresenter = new BingPictureFragmentPresenterImpl();
        mBingPictureFragmentPresenter.attach(this);
        // mModuleRecyclerView
        mModuleRecyclerViewDataSource = new ArrayList<>();
        mModuleRecyclerViewAdapter = new BingPictureModuleListAdapter(mModuleRecyclerViewDataSource, mActivity);
        mModuleRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mModuleRecyclerView.setAdapter(mModuleRecyclerViewAdapter);
        // mSwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(() -> mBingPictureFragmentPresenter.queryModuleAndTopic());
        // 初始化完成，请求一次
        mBingPictureFragmentPresenter.queryModuleAndTopic();
    }

    @Override
    public void onDestroy() {
        mBingPictureFragmentPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_bing_picture;
    }

    @Override
    public void showProgress() {
        mErrorPageViewGroup.setVisibility(View.GONE);
        if(!mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void showErrorPage() {
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mModuleRecyclerView.setVisibility(View.GONE);
        mErrorPageViewGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String message) {
        ToastUtil.show(obtainActivity(), message);
    }
}
