package com.github.xiaogegechen.bing.view.impl;

import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.xiaogegechen.bing.Constants;
import com.github.xiaogegechen.bing.R;
import com.github.xiaogegechen.bing.adapter.ImageListAdapter;
import com.github.xiaogegechen.bing.model.Image;
import com.github.xiaogegechen.bing.model.Topic;
import com.github.xiaogegechen.bing.model.event.NotifyGotoBigPicEvent;
import com.github.xiaogegechen.bing.presenter.IBingTopicDetailActivityPresenter;
import com.github.xiaogegechen.bing.presenter.impl.BingTopicDetailActivityPresenterImpl;
import com.github.xiaogegechen.bing.view.IBingTopicDetailActivityView;
import com.github.xiaogegechen.common.base.EventBusActivity;
import com.github.xiaogegechen.common.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class BingTopicDetailActivity extends EventBusActivity implements IBingTopicDetailActivityView {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private ViewGroup mErrorPageViewGroup;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    // mRecyclerView相关
    private List<Image> mRecyclerViewDataSource;
    private ImageListAdapter mRecyclerViewAdapter;

    private IBingTopicDetailActivityPresenter mBingTopicDetailActivityPresenter;

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.bing_activity_bing_topic_detail_toolbar);
        mRecyclerView = findViewById(R.id.bing_activity_bing_topic_detail_recycler);
        mErrorPageViewGroup = findViewById(R.id.bing_activity_bing_topic_detail_error);
        mSwipeRefreshLayout = findViewById(R.id.bing_activity_bing_topic_detail_refresh);
    }

    @Override
    public void initData() {
        mBingTopicDetailActivityPresenter = new BingTopicDetailActivityPresenterImpl();
        mBingTopicDetailActivityPresenter.attach(this);
        // 拿到传来的topic
        final Topic topic = getIntent().getParcelableExtra(Constants.INTENT_PARAM_NAME);
        String topicTitle = topic.getTitle();
        // mToolbar
        mToolbar.setTitle(topicTitle);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> finish());
        // mRecyclerView
        mRecyclerViewDataSource = new ArrayList<>();
        mRecyclerViewAdapter = new ImageListAdapter(mRecyclerViewDataSource, this);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        // mSwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(() -> mBingTopicDetailActivityPresenter.queryImageList(topic));
        // 做一次请求
        mBingTopicDetailActivityPresenter.queryImageList(topic);
    }

    @Override
    protected void onDestroy() {
        mBingTopicDetailActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.bing_activity_bing_topic_detail;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Subscribe
    public void handleNotifyGotoBigPicEvent(NotifyGotoBigPicEvent event){
        mBingTopicDetailActivityPresenter.gotoBigPicActivity(event);
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
        mRecyclerView.setVisibility(View.GONE);
        mErrorPageViewGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String message) {
        ToastUtil.show(this, message);
    }

    @Override
    public void showImageList(List<Image> imageList) {
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mErrorPageViewGroup.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerViewDataSource.clear();
        mRecyclerViewDataSource.addAll(imageList);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }
}
