package com.github.xiaogegechen.common.download;

import android.os.AsyncTask;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 下载逻辑的基类，下载进度计算方法未实现，由具体的实现者实现
 *
 * @param <Progress> 下载进度类型
 */
public abstract class BaseDownloadTask<Progress> extends AsyncTask<String, Progress, Integer> {

    /**
     * 下载成功
     */
    public static final int SUCCESS = 0;

    /**
     * 下载失败
     */
    public static final int FAILED = 1;

    /**
     * 下载被暂停
     */
    public static final int PAUSED = 2;

    /**
     * 默认的下载步长，一次下载1K
     */
    private static final int STEP = 1024;

    /**
     * 下载使用的okHttpClient
     */
    private OkHttpClient mOkHttpClient;

    /**
     * 文件保存位置
     */
    private File mFile;

    /**
     * 监听器
     */
    private BaseDownloadListener<Progress> mDownloadListener;

    /**
     * 步长
     */
    private int mStep = STEP;

    /**
     * 暂停标志位，当需要暂停下载时更改这个标志位，下载任务会轮询这个标志位，发现暂停马上停止下载
     */
    private boolean mIsPaused;

    public BaseDownloadTask(OkHttpClient okHttpClient, File file) {
        mOkHttpClient = okHttpClient;
        mFile = file;
    }

    public BaseDownloadTask(File file){
        mOkHttpClient = new OkHttpClient.Builder()
                .build();
        mFile = file;
    }

    /**
     * 设置下载监听
     *
     * @param downloadListener 监听器
     */
    public void setDownloadListener(BaseDownloadListener<Progress> downloadListener) {
        mDownloadListener = downloadListener;
    }

    /**
     * 设置下载步长
     *
     * @param step 步长
     */
    public void setStep(int step) {
        mStep = step;
    }

    /**
     * 暂停下载
     */
    public void pause(){
        mIsPaused = true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDownloadListener.onStart();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        try{
            String url = strings[0];
            long downloadedLength = 0;
            if(mFile.exists()){
                downloadedLength = mFile.length();
            }else{
                mFile.createNewFile();
            }
            long contentLength = Utils.getTotalLength(url, mOkHttpClient);
            if(contentLength == -1){
                return FAILED;
            }else if(contentLength == downloadedLength){
                return SUCCESS;
            }
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .build();
            Response response = mOkHttpClient.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                is = body.byteStream();
                savedFile = new RandomAccessFile(mFile, "rw");
                savedFile.seek(downloadedLength);
                byte[] bytes = new byte[mStep];
                int total = 0;
                int len;
                while ((len = is.read(bytes)) != -1){
                    if(mIsPaused){
                        return PAUSED;
                    }else{
                        total = total + len;
                        savedFile.write(bytes, 0, len);
                        Progress progress = calculateProgress(downloadedLength + total, contentLength);
                        publishProgress(progress);
                    }
                }
                body.close();
                return SUCCESS;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (is != null) {
                    is.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return FAILED;
    }

    @Override
    protected void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
        mDownloadListener.onProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case SUCCESS:
                mDownloadListener.onSuccess();
                break;
            case FAILED:
                mDownloadListener.onFailed();
                break;
            case PAUSED:
                mDownloadListener.onPaused();
                break;
            default:
                break;
        }
    }

    /**
     * 计算进度，这个进度是监听器的onProgress()方法的回调参数
     *
     * @param downloadedLength 已经下载的字节数
     * @param totalLength 文件大小
     * @return 进度
     */
    public abstract Progress calculateProgress(long downloadedLength, long totalLength);
}
