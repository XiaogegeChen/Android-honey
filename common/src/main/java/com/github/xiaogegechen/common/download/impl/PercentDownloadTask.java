package com.github.xiaogegechen.common.download.impl;

import com.github.xiaogegechen.common.download.BaseDownloadTask;
import com.github.xiaogegechen.common.download.Utils;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * 下载进度是百分比的异步下载任务
 */
public class PercentDownloadTask extends BaseDownloadTask<Float> {
    public PercentDownloadTask(OkHttpClient okHttpClient, File file) {
        super(okHttpClient, file);
    }

    public PercentDownloadTask(File file){
        super(file);
    }

    @Override
    public Float calculateProgress(long downloadedLength, long totalLength) {
        return Utils.round((float) (downloadedLength * 100.0 / totalLength));
    }
}
