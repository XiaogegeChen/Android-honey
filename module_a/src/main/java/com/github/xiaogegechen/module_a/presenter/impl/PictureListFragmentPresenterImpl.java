package com.github.xiaogegechen.module_a.presenter.impl;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.module_a.Api;
import com.github.xiaogegechen.module_a.Constants;
import com.github.xiaogegechen.module_a.model.PictureItem;
import com.github.xiaogegechen.module_a.model.PictureJSON;
import com.github.xiaogegechen.module_a.model.SentenceJSON;
import com.github.xiaogegechen.module_a.presenter.IPictureListFragmentPresenter;
import com.github.xiaogegechen.module_a.view.IPictureListFragmentView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PictureListFragmentPresenterImpl implements IPictureListFragmentPresenter {

    // handler的message type
    private static final int MSG_PICTURE = 0;
    private static final int MSG_SENTENCE = 1;
    private static final int COUNT_INVALID = -1;

    private IPictureListFragmentView mPictureListFragmentView;

    private Retrofit mMyServerRetrofit;

    private Call<PictureJSON> mPictureCall;
    private Call<SentenceJSON> mSentenceCall;

    // 两个线程的状态标记位，需要保证可见性，每次请求开始置为false，请求结束置为true
    private volatile boolean mPictureFlag = false;
    private volatile boolean mSentenceFlag = false;
    // 两个线程的请求返回值，需要保证可见性，每次请求开始置为null
    private volatile PictureJSON mPictureJSON;
    private volatile SentenceJSON mSentenceJSON;
    // 服务端的资源总数
    private int mPictureTotalCount = COUNT_INVALID;
    private int mSentenceTotalCount = COUNT_INVALID;
    // 当前offset，每次请求都更新，指向下一部分资源
    private int mCurrentOffset = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_PICTURE:
                    mPictureFlag = true;
                    mPictureJSON = (PictureJSON) msg.obj;
                    // 合并结果并显示
                    mergeDataAndRenderUI();
                    break;
                case MSG_SENTENCE:
                    mSentenceFlag = true;
                    mSentenceJSON = (SentenceJSON) msg.obj;
                    // 合并结果并显示
                    mergeDataAndRenderUI();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 对拿到的数据合并更新ui
     */
    private void mergeDataAndRenderUI(){
        if(mPictureFlag && mSentenceFlag){
            if(isPictureSuccess(mPictureJSON) && isSentenceSuccess(mSentenceJSON)){
                // 成功的返回
                PictureJSON.Result pictureJSONResult = mPictureJSON.getResult();
                SentenceJSON.Result sentenceJSONResult = mSentenceJSON.getResult();
                // 先查看totalCount是否赋值
                if(mPictureTotalCount == COUNT_INVALID){
                    mPictureTotalCount = Integer.parseInt(pictureJSONResult.getTotalCount());
                }
                if(mSentenceTotalCount == COUNT_INVALID){
                    mSentenceTotalCount = Integer.parseInt(sentenceJSONResult.getTypeTotal());
                }
                // 主体数据部分
                List<PictureJSON.Picture> pictureList = pictureJSONResult.getPictureList();
                List<SentenceJSON.Sentence> sentenceList = sentenceJSONResult.getSentenceList();
                // 取最小值，舍弃多余的数据，一般都是舍弃的sentence
                int size = Math.min(pictureList.size(), sentenceList.size());
                List<PictureItem> pictureItemList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    PictureJSON.Picture picture = pictureList.get(i);
                    SentenceJSON.Sentence sentence = sentenceList.get(i);
                    PictureItem item = combinePictureAndSentence(picture, sentence);
                    pictureItemList.add(item);
                }
                mPictureListFragmentView.showPictureList(pictureItemList);
                // mOffset自增，指向下一部分资源
                mCurrentOffset = mCurrentOffset + Constants.ITEM_COUNT_PER_PAGE;
                // 如果任何一个资源请求完毕，则显示没有更多
                if(mCurrentOffset >= Math.min(mPictureTotalCount, mSentenceTotalCount)){
                    mPictureListFragmentView.showDone();
                }
            }else{
                // 失败的返回
                mPictureListFragmentView.showErrorPage();
            }
        }
    }

    @Override
    public void attach(IPictureListFragmentView pictureListFragmentView) {
        mPictureListFragmentView = pictureListFragmentView;
        mMyServerRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.MY_SERVER_BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void queryPictureAndSentence(int type) {
        // TODO 使用线程池
        mPictureListFragmentView.showProgress();
        new Thread(() -> queryPicture(type)).start();
        new Thread(() -> querySentence(type)).start();
    }

    /**
     * 从服务端请求图片url地址，这个方法会阻塞，需要在子线程运行
     * @param type 图片类型
     */
    private void queryPicture(int type){
        mPictureFlag = false;
        mPictureJSON = null;
        mPictureCall = mMyServerRetrofit.create(Api.class).queryPicture(
                String.valueOf(type),
                String.valueOf(mCurrentOffset),
                String.valueOf(Constants.ITEM_COUNT_PER_PAGE)
        );
        Message message = mHandler.obtainMessage();
        message.what = MSG_PICTURE;
        try {
            Response<PictureJSON> pictureResponse = mPictureCall.execute();
            message.obj = pictureResponse.body();
        } catch (IOException e) {
            // 请求失败，直接结束
            message.obj = null;
            e.printStackTrace();
        }
        // 统一由handler处理
        mHandler.sendMessage(message);
    }

    /**
     * 从服务端请求短句集合，这个方法会阻塞，需要在子线程运行
     * @param type 短句类型
     */
    private void querySentence(int type){
        mSentenceFlag = false;
        mSentenceJSON = null;
        mSentenceCall = mMyServerRetrofit.create(Api.class).querySentence(
                String.valueOf(type),
                String.valueOf(mCurrentOffset),
                String.valueOf(Constants.ITEM_COUNT_PER_PAGE)
        );
        Message message = mHandler.obtainMessage();
        message.what = MSG_SENTENCE;
        try {
            Response<SentenceJSON> sentenceResponse = mSentenceCall.execute();
            message.obj = sentenceResponse.body();
        } catch (IOException e) {
            // 请求失败，直接结束
            message.obj = null;
            e.printStackTrace();
        }
        // 统一由handler处理
        mHandler.sendMessage(message);
    }

    @Override
    public void detach() {
        RetrofitHelper.cancelCalls(mPictureCall, mSentenceCall);
        mPictureListFragmentView = null;
    }

    private static boolean isPictureSuccess(PictureJSON pictureJSON){
        return pictureJSON != null
                && Integer.parseInt(pictureJSON.getErrorCode()) == Constants.MY_SERVER_RESPONSE_SUCCESS;
    }

    private static boolean isSentenceSuccess(SentenceJSON sentenceJSON){
        return sentenceJSON != null
                && Integer.parseInt(sentenceJSON.getErrorCode()) == Constants.MY_SERVER_RESPONSE_SUCCESS;
    }

    private static PictureItem combinePictureAndSentence(PictureJSON.Picture picture, SentenceJSON.Sentence sentence){
        PictureItem item = new PictureItem();
        item.setRealUrl(picture.getRealUrl());
        item.setRealImageWidth(Integer.parseInt(picture.getRealImageWidth()));
        item.setRealImageHeight(Integer.parseInt(picture.getRealImageHeight()));
        item.setRealImageFileSize(Float.parseFloat(picture.getRealImageFileSize()));
        item.setCompressUrl(picture.getCompressUrl());
        item.setCompressImageWidth(Integer.parseInt(picture.getCompressImageWidth()));
        item.setCompressImageHeight(Integer.parseInt(picture.getCompressImageHeight()));
        item.setCompressImageFileSize(Float.parseFloat(picture.getCompressImageFileSize()));
        item.setDescription(sentence.getContent());
        return item;
    }
}
