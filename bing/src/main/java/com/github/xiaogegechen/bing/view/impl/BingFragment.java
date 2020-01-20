package com.github.xiaogegechen.bing.view.impl;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.xiaogegechen.bing.R;
import com.github.xiaogegechen.bing.adapter.ModuleListAdapter;
import com.github.xiaogegechen.bing.model.Module;
import com.github.xiaogegechen.bing.presenter.IBingFragmentPresenter;
import com.github.xiaogegechen.bing.presenter.impl.BingFragmentPresenterImpl;
import com.github.xiaogegechen.bing.view.IBingFragmentView;
import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.github.xiaogegechen.common.arouter.ARouterMap.BING_FRAGMENT_BING;

// NOTE: 这个fragment可能会有两个实例，一个是MainActivity中的，另一个实在BingPictureActivity中的，两个可能同时存在
// 通过activity进行区分

@Route(path = BING_FRAGMENT_BING)
public class BingFragment extends BaseFragment implements IBingFragmentView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mModuleRecyclerView;
    private ImageView mBackImageView;
    private ViewGroup mErrorPageViewGroup;
    private Activity mActivity;
    // mModuleRecyclerView相关
    private List<Module> mModuleRecyclerViewDataSource;
    private ModuleListAdapter mModuleRecyclerViewAdapter;

    private IBingFragmentPresenter mBingFragmentPresenter;

    @Override
    public void initView(@NotNull View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.bing_fragment_bing_refresh);
        mBackImageView = view.findViewById(R.id.bing_fragment_bing_title_bar_back);
        mModuleRecyclerView = view.findViewById(R.id.bing_fragment_bing_content_list);
        mErrorPageViewGroup = view.findViewById(R.id.bing_fragment_bing_error);
        mActivity = obtainActivity();
        // imm
        if(mActivity != null){
            StatusBarUtils.fillStatusBarByView(mActivity, view.findViewById(R.id.bing_fragment_bing_placeholder));
        }
    }

    @Override
    public void initData() {
        mBingFragmentPresenter = new BingFragmentPresenterImpl();
        mBingFragmentPresenter.attach(this);
        // mTitleBar，如果现在是在 BingPictureActivity 中，那么需要返回按钮可见，并且响应点击，但是在其它activity
        // 中不能保证这个按钮的功能，因此隐藏它就好了
        if(mActivity instanceof BingActivity){
            mBackImageView.setOnClickListener(v -> mActivity.finish());
        }else{
            mBackImageView.setVisibility(View.INVISIBLE);
        }
        // mModuleRecyclerView
        mModuleRecyclerViewDataSource = new ArrayList<>();
        mModuleRecyclerViewAdapter = new ModuleListAdapter(mModuleRecyclerViewDataSource, mActivity);
        mModuleRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mModuleRecyclerView.setAdapter(mModuleRecyclerViewAdapter);
        // mSwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeResources(R.color.bing_color_primary, R.color.bing__color_primary_dark);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mBingFragmentPresenter.queryModuleAndTopic());
        // 初始化完成，请求一次
        mBingFragmentPresenter.queryModuleAndTopic();
    }

    @Override
    public void onDestroy() {
        mBingFragmentPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.bing_fragment_bing;
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

    @Override
    public void showModuleAndTopic(List<Module> moduleList) {
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mErrorPageViewGroup.setVisibility(View.GONE);
        mModuleRecyclerView.setVisibility(View.VISIBLE);
        mModuleRecyclerViewDataSource.clear();
        mModuleRecyclerViewDataSource.addAll(moduleList);
        mModuleRecyclerViewAdapter.notifyDataSetChanged();
    }
}
