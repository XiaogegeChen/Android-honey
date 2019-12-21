package com.github.xiaogegechen.bing.presenter.impl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.xiaogegechen.bing.Constants;
import com.github.xiaogegechen.bing.R;
import com.github.xiaogegechen.bing.model.Image;
import com.github.xiaogegechen.bing.presenter.IBigPicActivityPresenter;
import com.github.xiaogegechen.bing.view.IBigPicActivityView;
import com.github.xiaogegechen.common.util.ImageUtil;
import com.github.xiaogegechen.common.util.ShareUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BigPicActivityPresenterImpl implements IBigPicActivityPresenter {

    private static final int MSG_SAVE_SUCCESS = 1000;
    private static final int MSG_SAVE_FAILURE = 1001;

    private IBigPicActivityView mBigPicActivityView;
    private Activity mActivity;
    // 标志位，是否正在下载，会在子线程更改，要保证可见性
    private volatile boolean mIsDownloading;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_SAVE_SUCCESS:
                    String filePath = (String) msg.obj;
                    mBigPicActivityView.showToast(mActivity.getResources().getString(R.string.bing_big_pic_down_ok, filePath));
                    break;
                case MSG_SAVE_FAILURE:
                    String message = (String) msg.obj;
                    mBigPicActivityView.showToast(mActivity.getResources().getString(R.string.bing_big_pic_down_fail, message));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void attach(IBigPicActivityView bigPicActivityView) {
        mBigPicActivityView = bigPicActivityView;
        mActivity = (Activity) mBigPicActivityView;
    }

    @Override
    public void detach() {
        mHandler = null;
        mBigPicActivityView = null;
    }

    @Override
    public void download(Image image) {
        if(mIsDownloading){
            // 正在下载中
            mBigPicActivityView.showToast(mActivity.getResources().getString(R.string.bing_big_pic_downing));
        }else{
            // 开始下载
            mIsDownloading = true;
            mBigPicActivityView.showToast(mActivity.getResources().getString(R.string.bing_big_pic_start));
            // TODO 线程池
            new Thread(() -> {
                ImageUtil.INSTANCE.saveImage(image.getRealUrl(), Constants.IMAGE_DIR, mActivity, new ImageUtil.SaveImageCallback() {
                    @Override
                    public void onSuccess(@NotNull String filePath) {
                        mIsDownloading = false;
                        if(mHandler != null){
                            Message msg = mHandler.obtainMessage();
                            msg.what = MSG_SAVE_SUCCESS;
                            msg.obj = filePath;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFailure(@Nullable String message) {
                        mIsDownloading = false;
                        if(mHandler != null){
                            Message msg = mHandler.obtainMessage();
                            msg.what = MSG_SAVE_FAILURE;
                            msg.obj = message;
                            mHandler.sendMessage(msg);
                        }
                    }
                });
            }).start();
        }
    }

    @Override
    public void share(String url) {
        Glide.with(mActivity)
                .asBitmap()
                .load(url)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@androidx.annotation.Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        mBigPicActivityView.showToast(Constants.UNKNOWN_ERROR);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(final Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        // 需要动态申请写入权限
                        XXPermissions.with(mActivity)
                                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                                .request(new OnPermission() {
                                    @Override
                                    public void hasPermission(List<String> granted, boolean isAll) {
                                        ShareUtils.shareBitmap(resource, mActivity);
                                    }

                                    @Override
                                    public void noPermission(List<String> denied, boolean quick) {
                                        if(quick){
                                            // 权限被永久拒绝了
                                            mBigPicActivityView.showToast(mActivity.getResources().getString(R.string.bing_big_pic_permission_denied_forever));
                                            XXPermissions.gotoPermissionSettings(mActivity);
                                        }else{
                                            mBigPicActivityView.showToast(mActivity.getResources().getString(R.string.bing_big_pic_permission_denied));
                                        }
                                    }
                                });
                        return false;
                    }
                })
                .submit();
    }

    @Override
    public void viewOrigin(Image image) {
        mBigPicActivityView.showOrigin(image);
    }
}
