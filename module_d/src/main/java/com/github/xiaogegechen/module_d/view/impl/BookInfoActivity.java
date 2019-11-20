package com.github.xiaogegechen.module_d.view.impl;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.util.ImageParam;
import com.github.xiaogegechen.common.util.ImageUtil;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_d.Constants;
import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.adapter.BookInfoAdapter;
import com.github.xiaogegechen.module_d.model.db.BookInDB;
import com.github.xiaogegechen.module_d.presenter.IBookInfoActivityPresenter;
import com.github.xiaogegechen.module_d.presenter.impl.BookInfoActivityPresenterImpl;
import com.github.xiaogegechen.module_d.view.IBookInfoActivityView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.jetbrains.annotations.NotNull;

public class BookInfoActivity extends BaseActivity implements IBookInfoActivityView {

    private static final String TAG = "BookInfoActivity";

    private Toolbar mToolbar;
    private ImageView mBookImageView;
    private RecyclerView mRecyclerView;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private View mPlaceholderView;

    // 坍塌布局坍塌后的颜色
    private int mCollapsingToolbarLayoutContentScrimColor;

    private IBookInfoActivityPresenter mBookInfoActivityPresenter;

    @Override
    public void initData() {
        mBookInfoActivityPresenter = new BookInfoActivityPresenterImpl();
        mBookInfoActivityPresenter.attach(this);
        // 坍塌布局坍塌后的颜色
        ColorDrawable contentScrimDrawable = (ColorDrawable) (mCollapsingToolbarLayout.getContentScrim());
        mCollapsingToolbarLayoutContentScrimColor = contentScrimDrawable == null ?
                getResources().getColor(R.color.module_d_book_color_primary) :
                contentScrimDrawable.getColor();
        // 传递过来的book对象
        final BookInDB book = getIntent().getParcelableExtra(Constants.INTENT_PARAM_NAME);

        // imm
        StatusBarUtils.setImmersive(this);
        StatusBarUtils.fillStatusBarByView(this, mPlaceholderView);
        // imageView
        ImageParam param = new ImageParam.Builder()
                .context(this)
                .imageView(mBookImageView)
                .error(getResources().getDrawable(R.drawable.module_d_load_book_failed))
                .url(book.getImg())
                .build();
        ImageUtil.INSTANCE.displayImage(param);
        // recyclerView 设置
        BookInfoAdapter adapter = new BookInfoAdapter(mBookInfoActivityPresenter.convertBookInDB2BookInfoItem(book));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        // 监听mAppBarLayout的滑动，从而改变标题
        mAppBarLayout.addOnOffsetChangedListener(((appBarLayout, i) -> {
            int offSet = -1 * i;
            // 考虑imm加进来的状态栏高度
            int critical = mCollapsingToolbarLayout.getScrimVisibleHeightTrigger() - StatusBarUtils.getHeight(this);
            LogUtil.d(TAG, "offset is : " + offSet + ", critical is : " + critical);
            if(offSet >= critical){
                // 坍塌了，那么标题要设置为图书名
                mCollapsingToolbarLayout.setTitle (book.getTitle());
                mPlaceholderView.setBackgroundColor(mCollapsingToolbarLayoutContentScrimColor);
            }else{
                // 没有坍塌不显示
                mCollapsingToolbarLayout.setTitle (" ");
                mPlaceholderView.setBackgroundColor(Color.TRANSPARENT);
            }
        }));
        // toolbar点击事件
        mToolbar.setNavigationOnClickListener(v -> finishAfterTransition());
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.module_d_activity_book_info_tool_bar);
        mBookImageView = findViewById(R.id.module_d_activity_book_info_image);
        mRecyclerView = findViewById(R.id.module_d_activity_book_info_recycler_view);
        mAppBarLayout = findViewById(R.id.module_d_activity_book_info_app_bar_layout);
        mCollapsingToolbarLayout = findViewById(R.id.module_d_activity_book_info_collapsing_tool_bar_layout);
        mPlaceholderView = findViewById(R.id.module_d_activity_book_info_placeholder);

        // 共享元素进出场效果
        Window window = getWindow();
        TransitionSet set = new TransitionSet();
        set.addTransition(new ChangeBounds());
        set.addTransition(new ChangeImageTransform());
        window.setSharedElementEnterTransition(set);
        window.setSharedElementExitTransition(set);
        // 共享元素
        ViewCompat.setTransitionName(mBookImageView, Constants.SHARED_ELEMENT_BOOK_IAMGE);
        // toolbar
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void onDestroy() {
        mBookInfoActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_activity_book_info;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void showProgress() {}

    @Override
    public void showErrorPage() {}

    @Override
    public void showToast(@NotNull String message) {
        ToastUtil.show(this, message);
    }
}
