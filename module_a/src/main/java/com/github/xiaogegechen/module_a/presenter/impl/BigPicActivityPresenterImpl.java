package com.github.xiaogegechen.module_a.presenter.impl;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.xiaogegechen.common.download.BaseDownloadTask;
import com.github.xiaogegechen.common.download.impl.DefaultDownloadListener;
import com.github.xiaogegechen.common.download.impl.PercentDownloadTask;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.MyTextUtils;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.design.view.ProgressButton;
import com.github.xiaogegechen.module_a.Constants;
import com.github.xiaogegechen.module_a.R;
import com.github.xiaogegechen.module_a.model.PictureItem;
import com.github.xiaogegechen.module_a.presenter.IBigPicActivityPresenter;
import com.github.xiaogegechen.module_a.view.IBigPicActivityView;

import java.io.File;

public class BigPicActivityPresenterImpl implements IBigPicActivityPresenter {

    private static final String TAG = "BigPicActivityPresenter";

    private IBigPicActivityView mBigPicActivityView;
    private Activity mActivity;

    // 表示当前是原图还是缩略图
    private volatile boolean mIsOrigin;
    // 下载原图到缓存的下载任务
    private BaseDownloadTask<Float> mDownloadTaskToCache;
    private RequestListener<Drawable> mLoadFromFileListener;
    // 下载原图的下载任务
    private BaseDownloadTask<Float> mDownloadTaskOfRealUrl;
    private volatile boolean mIsDownloadTaskOfRealUrlPaused;
    // 下载缩略图的下载任务
    private BaseDownloadTask<Float> mDownloadTaskOfCompressUrl;
    private volatile boolean mIsDownloadTaskOfCompressUrlPaused;

    @Override
    public void attach(IBigPicActivityView bigPicActivityView) {
        mBigPicActivityView = bigPicActivityView;
        mActivity = (Activity) mBigPicActivityView;
    }

    @Override
    public void detach() {
        RetrofitHelper.cancelDownloadTasks(
                mDownloadTaskToCache,
                mDownloadTaskOfRealUrl,
                mDownloadTaskOfCompressUrl
        );
        mBigPicActivityView = null;
    }

    @Override
    public void download(String url, int flag, ProgressButton progressButton) {
        if(flag == FLAG_DOWN_REAL){
            downloadReal(url, progressButton);
        }else if(flag == FLAG_DOWN_COMPRESS){
            downloadCompress(url, progressButton);
        }
    }

    private void downloadReal(String realUrl, final ProgressButton progressButton){
        String fileName = MyTextUtils.md5DigestAsHex(realUrl);
        final File file = new File(mActivity.getExternalFilesDir(Constants.PIC_DIR_REAL_NAME), fileName);
        if(mIsDownloadTaskOfRealUrlPaused || mDownloadTaskOfRealUrl == null){
            // 暂停状态或第一次被点击，重新下载
            LogUtil.i(TAG, "start download real pic");
            mDownloadTaskOfRealUrl = new PercentDownloadTask(file);
            mDownloadTaskOfRealUrl.setDownloadListener(new DefaultDownloadListener<Float>(){
                @Override
                public void onStart() {
                    progressButton.setWorking(true);
                    mIsDownloadTaskOfRealUrlPaused = false;
                }

                @Override
                public void onSuccess() {
                    progressButton.setWorking(false);
                    progressButton.setText(obtainString(R.string.module_a_big_pic_down_ok_brief));
                    mBigPicActivityView.showToast(obtainString(R.string.module_a_big_pic_down_ok, file.getAbsolutePath()));
                    mIsDownloadTaskOfRealUrlPaused = true;
                }

                @Override
                public void onProgress(Float aFloat) {
                    progressButton.setProgress(aFloat.intValue());
                    progressButton.setText(obtainString(R.string.module_a_big_pic_downing_detail, aFloat + "%"));
                }

                @Override
                public void onFailed() {
                    progressButton.setText(obtainString(R.string.module_a_big_pic_down_fail_retry));
                    mIsDownloadTaskOfRealUrlPaused = true;
                }

                @Override
                public void onPaused() {
                    progressButton.setText(obtainString(R.string.module_a_big_pic_down_continue));
                    mIsDownloadTaskOfRealUrlPaused = true;
                }
            });
            mDownloadTaskOfRealUrl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, realUrl);
        }else{
            // 非暂停状态
            LogUtil.i(TAG, "current status is " + mDownloadTaskOfRealUrl.getStatus());
            if(mDownloadTaskOfRealUrl.getStatus() == AsyncTask.Status.RUNNING){
                LogUtil.i(TAG, "downloading real pic, begin to pause");
                // 说明正在下载，要暂停
                mDownloadTaskOfRealUrl.pause();
            }
        }
    }

    private void downloadCompress(String compressUrl, final ProgressButton progressButton){
        String fileName = MyTextUtils.md5DigestAsHex(compressUrl);
        final File file = new File(mActivity.getExternalFilesDir(Constants.PIC_DIR_REAL_NAME), fileName);
        if(mIsDownloadTaskOfCompressUrlPaused || mDownloadTaskOfCompressUrl == null){
            // 暂停状态或第一次被点击，重新下载
            LogUtil.i(TAG, "start download compress pic");
            mDownloadTaskOfCompressUrl = new PercentDownloadTask(file);
            mDownloadTaskOfCompressUrl.setDownloadListener(new DefaultDownloadListener<Float>(){
                @Override
                public void onStart() {
                    progressButton.setWorking(true);
                    mIsDownloadTaskOfCompressUrlPaused = false;
                }

                @Override
                public void onSuccess() {
                    progressButton.setWorking(false);
                    progressButton.setText(obtainString(R.string.module_a_big_pic_down_ok_brief));
                    mBigPicActivityView.showToast(obtainString(R.string.module_a_big_pic_down_ok, file.getAbsolutePath()));
                    mIsDownloadTaskOfCompressUrlPaused = true;
                }

                @Override
                public void onProgress(Float aFloat) {
                    progressButton.setProgress(aFloat.intValue());
                    progressButton.setText(obtainString(R.string.module_a_big_pic_downing_detail, aFloat + "%"));
                }

                @Override
                public void onFailed() {
                    progressButton.setText(obtainString(R.string.module_a_big_pic_down_fail_retry));
                    mIsDownloadTaskOfCompressUrlPaused = true;
                }

                @Override
                public void onPaused() {
                    progressButton.setText(obtainString(R.string.module_a_big_pic_down_continue));
                    mIsDownloadTaskOfCompressUrlPaused = true;
                }
            });
            mDownloadTaskOfCompressUrl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, compressUrl);
        }else{
            // 非暂停状态
            LogUtil.i(TAG, "current status is " + mDownloadTaskOfCompressUrl.getStatus());
            if(mDownloadTaskOfCompressUrl.getStatus() == AsyncTask.Status.RUNNING){
                LogUtil.i(TAG, "downloading compress pic, begin to pause");
                // 说明正在下载，要暂停
                mDownloadTaskOfCompressUrl.pause();
            }
        }
    }

    @Override
    public void share(String url) {

    }

    @Override
    public void viewOrigin(PictureItem pictureItem) {
        // 下载中提示正在下载，并不处理
        if(mDownloadTaskToCache != null && mDownloadTaskToCache.getStatus() == AsyncTask.Status.RUNNING){
            mBigPicActivityView.showToast(obtainString(R.string.module_a_big_pic_downing));
            return;
        }
        if(mIsOrigin){
            // 已经是原图，直接提示
            mBigPicActivityView.showToast(obtainString(R.string.module_a_big_pic_view_ok));
        }else{
            if(mLoadFromFileListener == null){
                mLoadFromFileListener = new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (mBigPicActivityView != null) {
                            mBigPicActivityView.showToast(obtainString(R.string.module_a_big_pic_view_failed));
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mIsOrigin = true;
                        if (mBigPicActivityView != null) {
                            mBigPicActivityView.showToast(obtainString(R.string.module_a_big_pic_view_ok));
                            mBigPicActivityView.hideViewOriginalButton();
                        }
                        return false;
                    }
                };
            }
            // 查看缓存文件
            String fileName = MyTextUtils.md5DigestAsHex(pictureItem.getRealUrl());
            final File file = new File(mActivity.getCacheDir(), fileName);
            // 文件存在不存在都启动下载，要是存在的话会断点续传
            if(mDownloadTaskToCache == null || mDownloadTaskToCache.getStatus() == AsyncTask.Status.FINISHED){
                mDownloadTaskToCache = new PercentDownloadTask(file);
                mDownloadTaskToCache.setDownloadListener(new DefaultDownloadListener<Float>(){
                    @Override
                    public void onProgress(Float aFloat) {
                        if (mBigPicActivityView != null) {
                            mBigPicActivityView.showViewOriginalProgress(aFloat + "%");
                        }
                    }

                    @Override
                    public void onFailed() {
                        if (mBigPicActivityView != null) {
                            mBigPicActivityView.showToast(obtainString(R.string.module_a_big_pic_view_failed));
                        }
                    }

                    @Override
                    public void onSuccess() {
                        // 显示原图
                        if (mBigPicActivityView != null) {
                            mBigPicActivityView.showImageFromFile(file, mLoadFromFileListener);
                        }
                    }
                });
            }
            mDownloadTaskToCache.execute(pictureItem.getRealUrl());
        }
    }

    private String obtainString(int stringId){
        return mActivity.getResources().getString(stringId);
    }

    private String obtainString(int stringId, String... param){
        return mActivity.getString(stringId, param);
    }
}
