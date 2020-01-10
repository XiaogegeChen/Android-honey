package com.github.xiaogegechen.common.download;

/**
 * 下载任务监听器，在下载中不同阶段做不同的回调
 *
 * @param <Progress> 下载进度的类型
 */
public interface BaseDownloadListener<Progress> {
    /**
     * 开始下载
     */
    void onStart();

    /**
     * 下载中，下载进度变化
     *
     * @param progress 下载进度
     */
    void onProgress(Progress progress);

    /**
     * 下载被暂停
     */
    void onPaused();

    /**
     * 下载成功
     */
    void onSuccess();

    /**
     * 下载失败
     */
    void onFailed();
}
