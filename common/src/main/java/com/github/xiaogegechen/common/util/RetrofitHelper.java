package com.github.xiaogegechen.common.util;

import com.github.xiaogegechen.common.download.BaseDownloadTask;

import retrofit2.Call;

public class RetrofitHelper {
    /**
     * 取消请求
     * @param call 请求
     */
    public static void cancelCall(Call call){
        if(call != null && !call.isCanceled()){
            call.cancel();
        }
    }

    /**
     * 批量取消请求
     * @param calls 多个网络请求
     */
    public static void cancelCalls(Call... calls){
        for (Call call : calls) {
            cancelCall(call);
        }
    }

    /**
     * 取消下载任务，不是真正取消，而是暂停，保证已经下载的文件不被损坏
     *
     * @param baseDownloadTask 下载任务
     */
    public static void cancelDownloadTask(BaseDownloadTask<?> baseDownloadTask){
        if (baseDownloadTask != null) {
            baseDownloadTask.pause();
        }
    }

    /**
     * 批量取消下载任务
     *
     * @param baseDownloadTasks 下载任务
     */
    public static void cancelDownloadTasks(BaseDownloadTask<?>... baseDownloadTasks){
        for (BaseDownloadTask<?> baseDownloadTask : baseDownloadTasks) {
            cancelDownloadTask(baseDownloadTask);
        }
    }
}
