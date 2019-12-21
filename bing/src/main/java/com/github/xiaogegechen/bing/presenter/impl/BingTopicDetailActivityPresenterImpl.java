package com.github.xiaogegechen.bing.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.bing.Api;
import com.github.xiaogegechen.bing.model.Image;
import com.github.xiaogegechen.bing.model.Topic;
import com.github.xiaogegechen.bing.model.event.NotifyGotoBigPicEvent;
import com.github.xiaogegechen.bing.model.json.ImageJSON;
import com.github.xiaogegechen.bing.presenter.IBingTopicDetailActivityPresenter;
import com.github.xiaogegechen.bing.view.IBingTopicDetailActivityView;
import com.github.xiaogegechen.bing.view.impl.BigPicActivity;
import com.github.xiaogegechen.common.Constants;
import com.github.xiaogegechen.common.network.CheckHelper;
import com.github.xiaogegechen.common.util.RetrofitHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BingTopicDetailActivityPresenterImpl implements IBingTopicDetailActivityPresenter {

    private IBingTopicDetailActivityView mBingTopicDetailActivityView;

    private Retrofit mBingPictureRetrofit;
    private Call<ImageJSON> mBingPictureImageCall;

    private Activity mActivity;

    @Override
    public void attach(IBingTopicDetailActivityView bingTopicDetailActivityView) {
        mBingTopicDetailActivityView = bingTopicDetailActivityView;
        mActivity = (Activity) mBingTopicDetailActivityView;
        mBingPictureRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.MY_SERVER_BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void detach() {
        RetrofitHelper.cancelCalls(mBingPictureImageCall);
        mBingTopicDetailActivityView = null;
    }

    @Override
    public void queryImageList(Topic topic) {
        mBingTopicDetailActivityView.showProgress();
        final String moduleType = topic.getModuleType();
        final String topicType = topic.getType();
        mBingPictureImageCall = mBingPictureRetrofit.create(Api.class).queryImage(moduleType, topicType);
        mBingPictureImageCall.enqueue(new Callback<ImageJSON>() {
            @Override
            public void onResponse(@NotNull Call<ImageJSON> call, @NotNull Response<ImageJSON> response) {
                final ImageJSON body = response.body();
                if(body != null){
                    // 校验数据
                    CheckHelper.checkResultFromMyServer(body, new com.github.xiaogegechen.common.network.Callback() {
                        @Override
                        public void onSuccess() {
                            mBingTopicDetailActivityView.showToast(com.github.xiaogegechen.bing.Constants.OK);
                            mBingTopicDetailActivityView.showImageList(convertImageJSON2ImageList(body, moduleType,topicType));
                        }

                        @Override
                        public void onFailure(String errorCode, String errorMessage) {
                            // 数据错误
                            mBingTopicDetailActivityView.showToast(errorMessage);
                            mBingTopicDetailActivityView.showErrorPage();
                        }
                    });
                }else{
                    // 未知错误，不知道是咋了
                    mBingTopicDetailActivityView.showToast(com.github.xiaogegechen.bing.Constants.UNKNOWN_ERROR);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ImageJSON> call, @NotNull Throwable t) {
                // 网络错误
                if(!call.isCanceled()){
                    mBingTopicDetailActivityView.showToast(com.github.xiaogegechen.bing.Constants.NETWORK_ERROR);
                    mBingTopicDetailActivityView.showErrorPage();
                }
            }
        });
    }

    @Override
    public void gotoBigPicActivity(NotifyGotoBigPicEvent event) {
        Intent intent = new Intent(mActivity, BigPicActivity.class);
        intent.putExtra(com.github.xiaogegechen.bing.Constants.INTENT_PARAM_NAME, event.getImage());
        // imageView 动画
        @SuppressWarnings("unchecked")
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                mActivity,
                new Pair<>(event.getImageView(), com.github.xiaogegechen.bing.Constants.BIG_PIC_NAME)
        );
        mActivity.startActivity(intent, activityOptions.toBundle());
    }

    /**
     * 把json转化为recyclerView展示需要的数据结构
     *
     * @param imageJSON  json数据
     * @param moduleType 图片所属module
     * @param topicType 图片所属topic
     * @return image的list
     */
    private static List<Image> convertImageJSON2ImageList(ImageJSON imageJSON, String moduleType, String topicType){
        List<Image> result = new ArrayList<>();
        List<ImageJSON.Result.Picture> pictureListInJSON = imageJSON.getResult().getPictureList();
        for (ImageJSON.Result.Picture pictureInJSON : pictureListInJSON) {
            Image image = new Image();
            image.setThumbnailUrl(pictureInJSON.getPictureThumbnailUrl());
            image.setRealUrl(pictureInJSON.getPictureRealUrl());
            image.setFromUrl(pictureInJSON.getPictureFromUrl());
            image.setModuleType(moduleType);
            image.setTopicType(topicType);
            result.add(image);
        }
        return result;
    }
}
