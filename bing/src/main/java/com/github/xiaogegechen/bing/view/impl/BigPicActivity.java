package com.github.xiaogegechen.bing.view.impl;

import android.graphics.Bitmap;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.xiaogegechen.bing.Constants;
import com.github.xiaogegechen.bing.R;
import com.github.xiaogegechen.bing.model.Image;
import com.github.xiaogegechen.bing.presenter.IBigPicActivityPresenter;
import com.github.xiaogegechen.bing.presenter.impl.BigPicActivityPresenterImpl;
import com.github.xiaogegechen.bing.view.IBigPicActivityView;
import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.util.ImageParam;
import com.github.xiaogegechen.common.util.ImageUtil;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.design.viewgroup.TitleBar;

public class BigPicActivity extends BaseActivity implements IBigPicActivityView {

    private PhotoView mPhotoView;
    private TitleBar mTitleBar;
    private ImageView mDownloadImageView;
    private ImageView mShareImageView;
    private TextView mViewOriginTextView;
    private ProgressBar mProgressBar;
    // 标记当前显示的图片是不是原图
    private boolean mIsOrigin;
    // 传来的图片bean
    private Image mImage;

    private IBigPicActivityPresenter mBigPicActivityPresenter;

    @Override
    public void initView() {
        mPhotoView = findViewById(R.id.big_pic_photo);
        mTitleBar = findViewById(R.id.big_pic_title);
        mDownloadImageView = findViewById(R.id.big_pic_down);
        mShareImageView = findViewById(R.id.big_pic_share);
        mViewOriginTextView = findViewById(R.id.big_pic_view_origin);
        mProgressBar = findViewById(R.id.big_pic_loading);
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
        // intent携带信息
        mImage = getIntent().getParcelableExtra(Constants.INTENT_PARAM_NAME);
        // mPhotoView
        ImageParam param = new ImageParam.Builder()
                .context(this)
                .url(mImage.getThumbnailUrl())
                .imageView(mPhotoView)
                .placeholder(getResources().getDrawable(R.drawable.bing_photo_loading))
                .error(getResources().getDrawable(R.drawable.bing_load_photo_failed))
                .build();
        ImageUtil.INSTANCE.displayImage(param);
        // mTitleBar
        mTitleBar.setListener(new TitleBar.OnArrowClickListener() {
            @Override
            public void onLeftClick() {
                supportFinishAfterTransition();
            }

            @Override
            public void onRightClick() {}
        });
        // mDownloadImageView
        mDownloadImageView.setOnClickListener(v -> mBigPicActivityPresenter.download(mImage));
        // mShareImageView
        mShareImageView.setOnClickListener(v -> {
            if(mIsOrigin){
                mBigPicActivityPresenter.share(mImage.getRealUrl());
            }else{
                mBigPicActivityPresenter.share(mImage.getThumbnailUrl());
            }
        });
        // mViewOriginTextView
        mViewOriginTextView.setOnClickListener(v -> mBigPicActivityPresenter.viewOrigin(mImage));
        // mProgressBar
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        mBigPicActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.bing_activity_big_pic;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void showProgress() {
        if(mProgressBar.getVisibility() != View.VISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgress(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorPage() {
        // ignore
    }

    @Override
    public void showToast(String message) {
        ToastUtil.show(message);
    }

    @Override
    public void showOrigin(Image image) {
        if(!mIsOrigin){
            showProgress();
            Glide.with(this)
                    .asBitmap()
                    .load(image.getRealUrl())
                    // 展位图使用目前的图片
                    .apply(new RequestOptions().placeholder(mPhotoView.getDrawable()))
                    .addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            hideProgress();
                            String message = getResources().getString(R.string.bing_big_pic_view_failed);
                            showToast(message);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            hideProgress();
                            String message = getResources().getString(R.string.bing_big_pic_view_ok);
                            showToast(message);
                            mIsOrigin = true;
                            return false;
                        }
                    })
                    .into(mPhotoView);
        }else{
            // 提示已经是原图
            String message = getResources().getString(R.string.bing_big_pic_view_ok);
            showToast(message);
        }
    }
}
