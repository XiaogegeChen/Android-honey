package com.github.xiaogegechen.weather.presenter.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.Constants;
import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.network.CheckHelper;
import com.github.xiaogegechen.common.test.TestActivity;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.weather.Api;
import com.github.xiaogegechen.weather.helper.SelectedCitySetHelper;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.SelectedCity;
import com.github.xiaogegechen.weather.model.json.DayPicJSON;
import com.github.xiaogegechen.weather.presenter.IWeatherFragmentPresenter;
import com.github.xiaogegechen.weather.view.IWeatherFragmentView;
import com.github.xiaogegechen.weather.view.impl.ManageCityActivity;
import com.github.xiaogegechen.weather.view.impl.SettingActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFragmentPresenterImpl implements IWeatherFragmentPresenter {

    private static final String TAG = "WeatherFragmentPresente";

    private IWeatherFragmentView mWeatherFragmentView;
    private Activity mActivity;
    private Retrofit mMyServerRetrofit;
    private Call<DayPicJSON> mDayPicCall;

    private ImageView mBgImageView;
    private MyTimerTask mMyTimerTask;
    private Timer mTimer;
    // 背景图是否可以改变
    private boolean mIsAllowBgChange;

    public WeatherFragmentPresenterImpl(ImageView bgImageView) {
        mBgImageView = bgImageView;
    }

    @Override
    public void attach(IWeatherFragmentView weatherFragmentView) {
        mWeatherFragmentView = weatherFragmentView;
        mActivity = ((BaseFragment) weatherFragmentView).obtainActivity();
        mMyServerRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.MY_SERVER_BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mIsAllowBgChange = PreferenceManager.getDefaultSharedPreferences(mActivity)
                .getBoolean(
                        com.github.xiaogegechen.weather.Constants.XML_KEY_ALLOW_BG_CHANGE,
                        false
                );
    }

    @Override
    public void detach() {
        mWeatherFragmentView = null;
        RetrofitHelper.cancelCalls(mDayPicCall);
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void gotoManageCityActivity() {
        Intent intent = new Intent(mActivity, ManageCityActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    public void gotoManageCityActivityIfNeeded() {
        if(!SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).hasSelectedCity()){
            // 没有添加任何城市，先添加城市
            gotoManageCityActivity();
        }else{
            // 批量添加进viewPager
            List<SelectedCity> selectedCityList = SelectedCitySetHelper.getInstance(mActivity.getApplicationContext()).getSelectedCity();
            List<CityInfo> cityInfoList = new ArrayList<>();
            for (SelectedCity selectedCity : selectedCityList) {
                CityInfo cityInfo = new CityInfo();
                cityInfo.setCityId(selectedCity.getId());
                cityInfo.setLocation(selectedCity.getLocation());
                cityInfoList.add(cityInfo);
            }
            mWeatherFragmentView.addCityList2ViewPager(cityInfoList);
        }
    }

    @Override
    public void queryDayPic() {
        mDayPicCall = mMyServerRetrofit.create(Api.class).queryDayPic();
        mDayPicCall.enqueue(new Callback<DayPicJSON>() {
            @Override
            public void onResponse(@NotNull Call<DayPicJSON> call, @NotNull Response<DayPicJSON> response) {
                if(!call.isCanceled()){
                    final DayPicJSON body = response.body();
                    if (body != null) {
                        CheckHelper.checkResultFromMyServer(body, new com.github.xiaogegechen.common.network.Callback() {
                            @Override
                            public void onSuccess() {
                                // 拿到url集合
                                List<DayPicJSON.Result.PicList> picList = body.getResult().getPicList();
                                List<String> picUrlList = new ArrayList<>();
                                for (DayPicJSON.Result.PicList list : picList) {
                                    picUrlList.add(list.getUrl());
                                }
                                LogUtil.i(TAG, "url list is -> " + picUrlList.toString());
                                // 显示最后一张图
                                Glide.with(mActivity)
                                        .asBitmap()
                                        .load(picUrlList.get(picList.size() - 1))
                                        .into(mBgImageView);
                                // 开启定时任务
                                mTimer = new Timer();
                                mMyTimerTask = new MyTimerTask(picUrlList);
                                mTimer.schedule(mMyTimerTask, 0, com.github.xiaogegechen.weather.Constants.BG_CHANGE_INTERVAL);
                            }

                            @Override
                            public void onFailure(String errorCode, String errorMessage) {
                                mWeatherFragmentView.showToast(com.github.xiaogegechen.weather.Constants.BG_IMAGE_ERROR_MESSAGE);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<DayPicJSON> call, @NotNull Throwable t) {
                if(!call.isCanceled()){
                    // TODO 背景图加载失败
                }
            }
        });
    }

    @Override
    public void gotoSetting() {
        Intent intent = new Intent(mActivity, SettingActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    public void isAllowBgChange(boolean allowBgChange) {
        mIsAllowBgChange = allowBgChange;
    }

    @Override
    public void debug() {
        // 查看sp中存储的已添加城市
        Set<String> selectedCityStringSet = SelectedCitySetHelper
                .getInstance(mActivity.getApplicationContext())
                .getSelectedCitySet();
        if(selectedCityStringSet.isEmpty()){
            TestActivity.startDebug(mActivity, "no city selected");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (String s : selectedCityStringSet) {
            builder.append(s);
            builder.append("\n");
        }
        TestActivity.startDebug(mActivity, builder.toString());
    }

    private class MyTimerTask extends TimerTask{
        private static final float MIN_ALPHA = 0.5f;
        private List<String> mUrlList;
        private int mCurrentIndex;
        private ObjectAnimator mDisappearAnimator;
        private ObjectAnimator mAppearAnimator;

        MyTimerTask(List<String> urlList) {
            mUrlList = urlList;
            int defaultDuration = mActivity.getResources().getInteger(android.R.integer.config_shortAnimTime);
            mDisappearAnimator = ObjectAnimator.ofFloat(mBgImageView, View.ALPHA, 1f, MIN_ALPHA);
            mDisappearAnimator.setDuration(defaultDuration);
            mAppearAnimator = ObjectAnimator.ofFloat(mBgImageView, View.ALPHA, MIN_ALPHA, 1f);
            mAppearAnimator.setDuration(defaultDuration);
        }

        @Override
        public void run() {
            // 背景图不能改变，直接return
            if(!mIsAllowBgChange){
                return;
            }
            mCurrentIndex ++;
            if(mCurrentIndex > mUrlList.size() - 1){
                mCurrentIndex = 0;
            }
            LogUtil.i(TAG, "begin to change bg image, url is -> " + mUrlList.get(mCurrentIndex));
            Glide.with(mActivity)
                    .asBitmap()
                    .load(mUrlList.get(mCurrentIndex))
                    .addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            LogUtil.i(TAG, "change bg image failed");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            LogUtil.i(TAG, "get bitmap success");
                            // imageView先变为透明后更换图片，之后变为正常
                            mDisappearAnimator.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    mDisappearAnimator.removeListener(this);
                                    mBgImageView.setImageBitmap(resource);
                                    mAppearAnimator.start();
                                }
                            });
                            mDisappearAnimator.start();
                            return false;
                        }
                    })
                    .submit();
        }
    }
}
