package com.github.xiaogegechen.common.download.impl;

import com.github.xiaogegechen.common.download.BaseDownloadTask;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * 下载进度是字节数的下载任务
 */
public class SizeDownloadTask extends BaseDownloadTask<Long> {
    public SizeDownloadTask(OkHttpClient okHttpClient, File file) {
        super(okHttpClient, file);
    }

    public SizeDownloadTask(File file){
        super(file);
    }

    @Override
    public Long calculateProgress(long downloadedLength, long totalLength) {
        return downloadedLength;
    }
}
