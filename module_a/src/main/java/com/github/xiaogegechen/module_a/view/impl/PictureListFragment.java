package com.github.xiaogegechen.module_a.view.impl;

import android.view.View;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.xiaogegechen.common.adapter.BaseRecyclerViewAdapter;
import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_a.R;
import com.github.xiaogegechen.module_a.adapter.PictureListAdapter;
import com.github.xiaogegechen.module_a.model.PictureItem;
import com.github.xiaogegechen.module_a.presenter.IPictureListFragmentPresenter;
import com.github.xiaogegechen.module_a.presenter.impl.PictureListFragmentPresenterImpl;
import com.github.xiaogegechen.module_a.view.IPictureListFragmentView;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class PictureListFragment extends BaseFragment
        implements BaseRecyclerViewAdapter.OnScrollingListener, IPictureListFragmentView {

    // fragment对应的tabLayout不同tab
    public static final int TYPE_FIRST = 0;
    public static final int TYPE_SECOND = 1;
    public static final int TYPE_THIRD = 2;

    @IntDef({
            TYPE_FIRST,
            TYPE_SECOND,
            TYPE_THIRD
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TabType{}

    private RecyclerView mRecyclerView;
    private PictureListAdapter mAdapter;
    private List<PictureItem> mDataSource;

    // 类型
    private @TabType int mType;

    private IPictureListFragmentPresenter mPictureListFragmentPresenter;

    public void setType(@TabType int type) {
        mType = type;
    }

    @Override
    public void initView(@NotNull View view) {
        mRecyclerView = view.findViewById(R.id.module_d_fragment_picture_list_recycler);
    }

    @Override
    public void initData() {
        mPictureListFragmentPresenter = new PictureListFragmentPresenterImpl();
        mPictureListFragmentPresenter.attach(this);
        mDataSource = new ArrayList<>();
        mAdapter = new PictureListAdapter(mDataSource, obtainContext());
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mPictureListFragmentPresenter.queryPictureAndSentence(mType);
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_a_fragment_picture_list;
    }

    @Override
    public void onDestroy() {
        mPictureListFragmentPresenter.detach();
        super.onDestroy();
    }

    /**
     * {@link com.github.xiaogegechen.common.adapter.BaseRecyclerViewAdapter.OnScrollingListener}
     */
    @Override
    public void onScrolling(RecyclerView recyclerView) {}

    @Override
    public void onScrollTop(RecyclerView recyclerView) {}

    @Override
    public void onScrollDown(RecyclerView recyclerView) {
        // TODO 加载更多
        mPictureListFragmentPresenter.queryPictureAndSentence(mType);
    }

    @Override
    public void showProgress() {
        mAdapter.setState(PictureListAdapter.STATE_LOAD);
    }

    @Override
    public void showErrorPage() {
        mAdapter.setState(PictureListAdapter.STATE_ERROR);
    }

    @Override
    public void showToast(String message) {
        ToastUtil.show(message);
    }

    @Override
    public void showPictureList(List<PictureItem> pictureItemList) {
        // 发现第一次加数据后会自动滑动到最后一个，立马触发了下一次请求，体验不好，在这个位置修复
        boolean firstFlag = false;
        if (mDataSource.size() == 0){
            firstFlag = true;
        }
        mAdapter.addToEnd(pictureItemList);
        if (firstFlag){
            mRecyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void showDone() {
        mAdapter.setState(PictureListAdapter.STATE_DONE);
        // 取消上拉监听
        mAdapter.setListener(null);
    }
}
