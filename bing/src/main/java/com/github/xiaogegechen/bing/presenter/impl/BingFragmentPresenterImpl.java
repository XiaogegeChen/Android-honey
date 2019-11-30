package com.github.xiaogegechen.bing.presenter.impl;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.bing.Api;
import com.github.xiaogegechen.bing.model.Module;
import com.github.xiaogegechen.bing.model.Topic;
import com.github.xiaogegechen.bing.model.json.ModuleJSON;
import com.github.xiaogegechen.bing.model.json.TopicJSON;
import com.github.xiaogegechen.bing.presenter.IBingFragmentPresenter;
import com.github.xiaogegechen.bing.view.IBingFragmentView;
import com.github.xiaogegechen.common.Constants;
import com.github.xiaogegechen.common.network.Callback;
import com.github.xiaogegechen.common.network.CheckHelper;
import com.github.xiaogegechen.common.util.RetrofitHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// NOTE: 请求图片任务在子线程中完成，然后通过handler更新UI，这里的handler内部持有Activity，很可能内存泄露，因此在退出
// activity时要把handler置空。但是子线程任务可能会在activity销毁后才完成，所以使用handler发消息之前先判空，防止空指针
// 发生。

public class BingFragmentPresenterImpl implements IBingFragmentPresenter {

    // 网络状态异常
    private static final int NETWORK_ERROR = 1000;
    // 异常的返回数据，不是网络状态异常引起的
    private static final int DATA_ERROR = 10001;
    // 正确的返回数据
    private static final int OK = 1002;

    private IBingFragmentView mBingFragmentView;

    private Retrofit mBingRetrofit;
    private Call<ModuleJSON> mBingPictureModuleCall;
    private Call<TopicJSON> mBingPictureTopicCall;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DATA_ERROR:
                    // 取出错误信息并通过toast显示，同时显示错误页面
                    String[] errorArray = (String[]) msg.obj;
                    String errorMessage = errorArray[1];
                    mBingFragmentView.showToast(errorMessage);
                    mBingFragmentView.showErrorPage();
                    break;
                case NETWORK_ERROR:
                    // toast显示网络错误，同时显示错误页面
                    mBingFragmentView.showToast(com.github.xiaogegechen.bing.Constants.NETWORK_ERROR);
                    mBingFragmentView.showErrorPage();
                    break;
                case OK:
                    // 正确数据，toast显示成功，取出数据并显示
                    @SuppressWarnings("unchecked")
                    List<Module> moduleList = (List<Module>) msg.obj;
                    mBingFragmentView.showToast(com.github.xiaogegechen.bing.Constants.OK);
                    mBingFragmentView.showModuleAndTopic(moduleList);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void attach(IBingFragmentView bingFragmentView) {
        mBingFragmentView = bingFragmentView;
        mBingRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.MY_SERVER_BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void detach() {
        RetrofitHelper.cancelCalls(mBingPictureModuleCall, mBingPictureTopicCall);
        mHandler = null;
        mBingFragmentView = null;
    }

    @Override
    public void queryModuleAndTopic() {
        mBingFragmentView.showProgress();
        // 先取消上一个操作
        RetrofitHelper.cancelCalls(mBingPictureModuleCall, mBingPictureTopicCall);
        // TODO 使用线程池
        new Thread(this::queryModuleAndTopicInWorkThread).start();
    }

    /**
     * 子线程中请求bing套图的数据，耗时操作，会阻塞。
     */
    private void queryModuleAndTopicInWorkThread(){
        mBingPictureModuleCall = mBingRetrofit.create(Api.class).queryModule();
        try{
            // 先请求拿到所有module
            Response<ModuleJSON> response = mBingPictureModuleCall.execute();
            final ModuleJSON body = response.body();
            if(body != null){
                CheckHelper.checkResultFromMyServer(body, new Callback() {
                    @Override
                    public void onSuccess() {
                        // 遍历每一个module，请求拿到它们的topic并通知主线程handler
                        List<ModuleJSON.Result.Module> moduleListInJSON = body.getResult().getModuleList();
                        // module数量，通常都有5，6个
                        int moduleCount = moduleListInJSON.size();
                        // 请求到的结果填充到 moduleList 中
                        final List<Module> moduleList = new ArrayList<>();
                        // 记录每一个请求的状态，是网络错误，数据错误还是ok
                        final int[] queryStateArray = new int[moduleCount];
                        Arrays.fill(queryStateArray, OK);
                        // 存储错误信息，如果有请求出错的话，会将错误信息填进这个数组，第一个值为errorCode，第二为
                        // errorMessage。如果没有请求出从的话为""
                        final String[] errorArray = new String[2];
                        Arrays.fill(errorArray, "");
                        for (int i = 0; i < moduleCount; i++){
                            final int index = i;
                            ModuleJSON.Result.Module moduleInJSON = moduleListInJSON.get(index);
                            final String moduleTitle = moduleInJSON.getModuleTitle();
                            final String moduleType = moduleInJSON.getModuleType();
                            mBingPictureTopicCall = mBingRetrofit.create(Api.class).queryTopic(moduleType);
                            try{
                                // 发起请求 TODO 改为并行
                                Response<TopicJSON> topicResponse = mBingPictureTopicCall.execute();
                                final TopicJSON topicBody = topicResponse.body();
                                if(topicBody != null){
                                    // 校验
                                    CheckHelper.checkResultFromMyServer(topicBody, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Module module = new Module();
                                            module.setTitle(moduleTitle);
                                            module.setType(moduleType);
                                            module.setTopicList(convertTopicJSON2TopicList(topicBody, moduleType, moduleTitle));
                                            moduleList.add(module);
                                        }

                                        @Override
                                        public void onFailure(String errorCode, String errorMessage) {
                                            // 请求到了数据，但是不是正确的数据，通知主线程handler
                                            queryStateArray[index] = DATA_ERROR;
                                            errorArray[0] = errorCode;
                                            errorArray[1] = errorMessage;
                                        }
                                    });
                                }
                            }catch (IOException e){
                                e.printStackTrace();
                                // 这是网络状态的异常
                                if(!mBingPictureTopicCall.isCanceled()){
                                    queryStateArray[index] = NETWORK_ERROR;
                                }
                            }
                        }
                        // 评判结果，展示到ui.
                        // 如果全部成功，直接显示。
                        // 如果有失败的，但是成功数大于等于2 / 3，则按成功处理。
                        // 否则直接提示请求出错，让用户选择重新加载，错误信息显示只显示最后一个失败的请求的错误类型。
                        if(moduleList.size() >= (moduleCount * 2) / 3){
                            notifyDataOK(moduleList);
                        }else{
                            // 寻找错误类型并传递给handler
                            for (int i = queryStateArray.length - 1; i > -1; i--) {
                                int queryState = queryStateArray[i];
                                if(queryState == NETWORK_ERROR){
                                    notifyNetworkError();
                                }else if(queryState == DATA_ERROR){
                                    notifyDataError(errorArray[0], errorArray[1]);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorCode, String errorMessage) {
                        // 请求到了数据，但是不是正确的数据，通知主线程handler
                        notifyDataError(errorCode, errorMessage);
                    }
                });
            }
        }catch (IOException e){
            // 这是网络状态的异常
            e.printStackTrace();
            if(!mBingPictureModuleCall.isCanceled()){
                notifyNetworkError();
            }
        }
    }

    /**
     * 请求到了正确的数据，通知主线程handler
     *
     * @param moduleList 正确的数据
     */
    private void notifyDataOK(List<Module> moduleList){
        if (mHandler != null) {
            Message message = mHandler.obtainMessage();
            message.what = OK;
            message.obj = moduleList;
            mHandler.sendMessage(message);
        }
    }

    /**
     * 请求到了数据，但是不是正确的数据，通知主线程handler
     *
     * @param errorCode 错误码
     * @param errorMessage 错误信息
     */
    private void notifyDataError(String errorCode, String errorMessage) {
        if (mHandler != null) {
            Message message = mHandler.obtainMessage();
            message.what = DATA_ERROR;
            message.obj = new String[]{errorCode, errorMessage};
            mHandler.sendMessage(message);
        }
    }

    /**
     * 请求时发生网络错误，通知主线程handler
     */
    private void notifyNetworkError(){
        if (mHandler != null) {
            Message message = mHandler.obtainMessage();
            message.what = NETWORK_ERROR;
            mHandler.sendMessage(message);
        }
    }

    /**
     * 将请求的json转化为recyclerView使用的数据源
     *
     * @param topicJSON 原始json
     * @param moduleType 所属module的类型
     * @param moduleTitle 所属module的title
     *
     * @return 转化后的list
     */
    private static List<Topic> convertTopicJSON2TopicList(TopicJSON topicJSON, String moduleType, String moduleTitle){
        List<Topic> result = new ArrayList<>();
        List<TopicJSON.Result.Topic> topicListInJSON = topicJSON.getResult().getTopicList();
        for (TopicJSON.Result.Topic topicInJSON : topicListInJSON) {
            Topic topic = new Topic();
            topic.setCoverUrl(topicInJSON.getTopicCoverUrl());
            topic.setTitle(topicInJSON.getTopicTitle());
            topic.setModuleType(moduleType);
            topic.setModuleTitle(moduleTitle);
            topic.setType(topicInJSON.getTopicType());
            result.add(topic);
        }
        return result;
    }
}
