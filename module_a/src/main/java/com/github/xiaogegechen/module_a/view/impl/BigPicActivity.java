package com.github.xiaogegechen.module_a.view.impl;

import android.graphics.drawable.Drawable;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.xiaogegechen.common.base.EventBusActivity;
import com.github.xiaogegechen.common.util.MyTextUtils;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.design.viewgroup.DrawerLayout;
import com.github.xiaogegechen.design.viewgroup.TitleBar;
import com.github.xiaogegechen.module_a.Constants;
import com.github.xiaogegechen.module_a.R;
import com.github.xiaogegechen.module_a.adapter.PackingAdapter;
import com.github.xiaogegechen.module_a.model.PackingItem;
import com.github.xiaogegechen.module_a.model.PictureItem;
import com.github.xiaogegechen.module_a.model.event.NotifyPackingItemClickedEvent;
import com.github.xiaogegechen.module_a.presenter.IBigPicActivityPresenter;
import com.github.xiaogegechen.module_a.presenter.impl.BigPicActivityPresenterImpl;
import com.github.xiaogegechen.module_a.view.IBigPicActivityView;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class BigPicActivity extends EventBusActivity implements IBigPicActivityView {

    private static final float MAX_ALPHA = 0.4f;
    private static final float FACTOR = 0.5f;

    // 下载选择的flag
    public static final int FLAG_DOWN_REAL = 0;
    public static final int FLAG_DOWN_COMPRESS = 1;
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            FLAG_DOWN_REAL,
            FLAG_DOWN_COMPRESS,
    })
    public @interface DownFlag{}

    // 分享选择的flag
    public static final int FLAG_SHARE_REAL = 100;
    public static final int FLAG_SHARE_COMPRESS = 101;
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            FLAG_SHARE_REAL,
            FLAG_SHARE_COMPRESS
    })
    public @interface ShareFlag{}

    private PhotoView mPhotoView;
    private ProgressBar mProgressBar;
    private TitleBar mTitleBar;
    private TextView mViewOriginTextView;
    private ImageView mDownImageView;
    private ImageView mShareImageView;
    private View mDrawerBgView;
    private DrawerLayout mDrawerLayout;
    private ImageView mDrawerCloseImageView;
    private TextView mDrawerTitleTextView;
    private RecyclerView mDrawerContentRecyclerView;
    private List<PackingItem> mRecyclerViewDataSource;
    private PackingAdapter mRecyclerViewAdapter;

    private PictureItem mPictureItem;

    private List<PackingItem> mDownPackingList;
    private List<PackingItem> mSharePackingList;

    private IBigPicActivityPresenter mBigPicActivityPresenter;

    @Override
    public void initView() {
        mPhotoView = findViewById(R.id.big_pic_photo);
        mProgressBar = findViewById(R.id.big_pic_loading);
        mTitleBar = findViewById(R.id.big_pic_title);
        mViewOriginTextView = findViewById(R.id.big_pic_view_origin);
        mDownImageView = findViewById(R.id.big_pic_down);
        mShareImageView = findViewById(R.id.big_pic_share);
        mDrawerBgView = findViewById(R.id.big_pic_drawer_bg);
        mDrawerLayout = findViewById(R.id.big_pic_drawer);
        mDrawerCloseImageView = findViewById(R.id.big_pic_draw_head_disappear);
        mDrawerTitleTextView = findViewById(R.id.big_pic_draw_head_title);
        mDrawerContentRecyclerView = findViewById(R.id.big_pic_drawer_content);
        // imm
        StatusBarUtils.setImmersive(this);
        StatusBarUtils.fillStatusBarByView(this, findViewById(R.id.big_pic_vh));
        // 共享元素进出场效果
        Window window = getWindow();
        TransitionSet set = new TransitionSet();
        set.addTransition(new ChangeBounds());
        set.addTransition(new ChangeImageTransform());
        window.setSharedElementEnterTransition(set);
        window.setSharedElementExitTransition(set);
        // 共享元素
        ViewCompat.setTransitionName(mPhotoView, Constants.BIG_PIC_NAME);
    }

    @Override
    public void initData() {
        mBigPicActivityPresenter = new BigPicActivityPresenterImpl();
        mBigPicActivityPresenter.attach(this);
        // intent携带对象
        mPictureItem = getIntent().getParcelableExtra(Constants.BIG_PIC_INTENT_PARAM);
        // 初始化recyclerView的资源
        initPackingList();
        // mPhotoView
        Glide.with(this)
                .load(mPictureItem.getCompressUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.module_a_photo_loading)
                        .error(R.drawable.module_a_load_photo_failed))
                .into(mPhotoView);
        // mProgressBar
        mProgressBar.setVisibility(View.GONE);
        // mTitleBar
        mTitleBar.setListener(new TitleBar.OnArrowClickListener() {
            @Override
            public void onLeftClick() {
                supportFinishAfterTransition();
            }

            @Override
            public void onRightClick() {}
        });
        // mViewOriginTextView
        mViewOriginTextView.setText(getResources().getString(R.string.module_a_big_pic_view_origin, MyTextUtils.handleFileSize(mPictureItem.getRealImageFileSize())));
        mViewOriginTextView.setOnClickListener(v -> {
            // 显示原图
            mBigPicActivityPresenter.viewOrigin(mPictureItem);
        });
        ViewTreeObserver observer = mViewOriginTextView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mViewOriginTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mViewOriginTextView.setWidth(mViewOriginTextView.getWidth());
                mViewOriginTextView.setHeight(mViewOriginTextView.getHeight());
            }
        });
        // mDownImageView
        mDownImageView.setOnClickListener(v -> {
            // 下载
            mDrawerLayout.setVisibility(View.INVISIBLE);
            mDrawerTitleTextView.setText(getResources().getString(R.string.module_a_big_pic_down));
            mRecyclerViewDataSource.clear();
            mRecyclerViewDataSource.addAll(mDownPackingList);
            mRecyclerViewAdapter.notifyDataSetChanged();
            if(mDrawerLayout.getCurrentStatus() == DrawerLayout.OPEN){
                mDrawerLayout.setVisibility(View.VISIBLE);
            }else{
                mDrawerLayout.setVisibility(View.VISIBLE);
                mDrawerLayout.open();
            }
        });
        // mShareImageView
        mShareImageView.setOnClickListener(v -> {
            // 分享
            mDrawerLayout.setVisibility(View.INVISIBLE);
            mDrawerTitleTextView.setText(getResources().getString(R.string.module_a_big_pic_share));
            mRecyclerViewDataSource.clear();
            mRecyclerViewDataSource.addAll(mSharePackingList);
            mRecyclerViewAdapter.notifyDataSetChanged();
            if(mDrawerLayout.getCurrentStatus() == DrawerLayout.OPEN){
                mDrawerLayout.setVisibility(View.VISIBLE);
            }else{
                mDrawerLayout.setVisibility(View.VISIBLE);
                mDrawerLayout.open();
            }
        });
        // mDrawerBgView
        mDrawerBgView.setVisibility(View.GONE);
        // mDrawerLayout
        initDrawer();
        // mDrawerCloseImageView
        mDrawerCloseImageView.setOnClickListener(v -> {
            // 关闭抽屉
            disappearDrawer();
        });
        // mDrawerContentRecyclerView
        mDrawerContentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewDataSource = new ArrayList<>();
        mRecyclerViewAdapter = new PackingAdapter(mRecyclerViewDataSource);
        mDrawerContentRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        mBigPicActivityPresenter.detach();
        super.onDestroy();
    }

    private void initPackingList(){
        String[] downPackingArray = getResources().getStringArray(R.array.module_a_big_pic_down_pack);
        mDownPackingList = new ArrayList<>();
        for (int i = 0; i < downPackingArray.length; i++) {
            PackingItem item = new PackingItem();
            switch (i){
                case 0:
                    item.setFlag(FLAG_DOWN_REAL);
                    item.setContent(downPackingArray[i] + "(" + MyTextUtils.handleFileSize(mPictureItem.getRealImageFileSize()) + ")");
                    break;
                case 1:
                    item.setFlag(FLAG_DOWN_COMPRESS);
                    item.setContent(downPackingArray[i] + "(" + MyTextUtils.handleFileSize(mPictureItem.getCompressImageFileSize()) + ")");
                    break;
                default:
                    break;
            }
            mDownPackingList.add(item);
        }
        String[] sharePackingArray = getResources().getStringArray(R.array.module_a_big_pic_share_pack);
        mSharePackingList = new ArrayList<>();
        for (int i = 0; i < sharePackingArray.length; i++) {
            PackingItem item = new PackingItem();
            switch (i){
                case 0:
                    item.setFlag(FLAG_SHARE_REAL);
                    item.setContent(sharePackingArray[i] + "(" + MyTextUtils.handleFileSize(mPictureItem.getRealImageFileSize()) + ")");
                    break;
                case 1:
                    item.setFlag(FLAG_SHARE_COMPRESS);
                    item.setContent(sharePackingArray[i] + "(" + MyTextUtils.handleFileSize(mPictureItem.getCompressImageFileSize()) + ")");
                    break;
                default:
                    break;
            }
            mSharePackingList.add(item);
        }
    }

    private void initDrawer(){
        // 设置背景
        mDrawerLayout.setBgView(mDrawerBgView, MAX_ALPHA);
        mDrawerLayout.setFactor(FACTOR);
        // 最小高度是head的高度
        final View headView = findViewById(R.id.big_pic_drawer_head);
        final ViewTreeObserver observer = headView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                headView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mDrawerLayout.setMinHeight(headView.getHeight());
            }
        });
        // 先设置为 INVISIBLE，在关闭，之后设置为 GONE
        mDrawerLayout.setVisibility(View.INVISIBLE);
        mDrawerLayout.close(new DrawerLayout.AnimationCallback() {
            @Override
            public void onStart() {}

            @Override
            public void onEnd() {
                mDrawerLayout.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 抽屉消失
     */
    private void disappearDrawer(){
        // 抽屉消失
        if(mDrawerLayout.getCurrentStatus() == DrawerLayout.CLOSE){
            // 消失
            mDrawerLayout.setVisibility(View.GONE);
        }else{
            mDrawerLayout.close(new DrawerLayout.AnimationCallback() {
                @Override
                public void onStart() {}

                @Override
                public void onEnd() {
                    // 消失
                    mDrawerLayout.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mDrawerLayout.getVisibility() == View.VISIBLE){
                disappearDrawer();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_a_activity_big_pic;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void showOriginalImage(PictureItem pictureItem) {

    }

    @Override
    public void showImageFromFile(File file, RequestListener<Drawable> listener) {
        Glide.with(this)
                .load(file)
                .addListener(listener)
                .into(mPhotoView);
    }

    @Override
    public void showViewOriginalProgress(String progress) {
        mViewOriginTextView.setText(progress);
    }

    @Override
    public void hideViewOriginalButton() {
        mViewOriginTextView.setVisibility(View.INVISIBLE);
    }

    @Subscribe
    public void onHandleNotifyPackingItemClickedEvent(NotifyPackingItemClickedEvent event){
        switch (event.getPackingItem().getFlag()){
            case FLAG_DOWN_REAL:
                mBigPicActivityPresenter.download(
                        mPictureItem.getRealUrl(),
                        IBigPicActivityPresenter.FLAG_DOWN_REAL,
                        event.getProgressButton()
                );
                break;
            case FLAG_DOWN_COMPRESS:
                mBigPicActivityPresenter.download(
                        mPictureItem.getCompressUrl(),
                        IBigPicActivityPresenter.FLAG_DOWN_COMPRESS,
                        event.getProgressButton()
                );
                break;
            case FLAG_SHARE_REAL:
                break;
            case FLAG_SHARE_COMPRESS:
                break;
            default:
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showToast(String message) {
        ToastUtil.show(message);
    }
}
