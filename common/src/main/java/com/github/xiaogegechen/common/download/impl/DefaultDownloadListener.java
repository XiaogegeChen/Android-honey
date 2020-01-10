package com.github.xiaogegechen.common.download.impl;

import com.github.xiaogegechen.common.download.BaseDownloadListener;

public class DefaultDownloadListener<Progress> implements BaseDownloadListener<Progress> {
    @Override
    public void onStart() {}

    @Override
    public void onProgress(Progress progress) {}

    @Override
    public void onPaused() {}

    @Override
    public void onSuccess() {}

    @Override
    public void onFailed() {}
}
