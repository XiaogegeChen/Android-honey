package com.github.xiaogegechen.weather.presenter.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.weather.Api;
import com.github.xiaogegechen.weather.helper.BooleanValueManager;
import com.github.xiaogegechen.weather.helper.SelectedCitiesManager;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.SelectedCityForRvInMCAct;
import com.github.xiaogegechen.weather.model.db.SelectedCity;
import com.github.xiaogegechen.weather.model.json.BaseWeatherPicJSON;
import com.github.xiaogegechen.weather.model.json.DayPicJSON;
import com.github.xiaogegechen.weather.model.json.PrecipitationJSON;
import com.github.xiaogegechen.weather.model.json.SatelliteJSON;
import com.github.xiaogegechen.weather.model.json.SatelliteTypeJSON;
import com.github.xiaogegechen.weather.model.json.TemperatureJSON;
import com.github.xiaogegechen.weather.model.json.TopCityJSON;
import com.github.xiaogegechen.weather.model.json.VisibilityJSON;
import com.github.xiaogegechen.weather.model.json.WindJSON;
import com.github.xiaogegechen.weather.presenter.IWeatherFragmentPresenter;
import com.github.xiaogegechen.weather.view.IWeatherFragmentView;
import com.github.xiaogegechen.weather.view.impl.ManageCityActivity;
import com.github.xiaogegechen.weather.view.impl.SelectCityActivity;
import com.github.xiaogegechen.weather.view.impl.SettingActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
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

    private Retrofit mSearchHotCityRetrofit;
    private Retrofit mMyServerRetrofit;

    private Call<DayPicJSON> mDayPicCall;
    private Call<TopCityJSON> mHotCityCall;

    private Call<PrecipitationJSON> mPrecipitationCall;
    private Call<TemperatureJSON> mTemperatureCall;
    private Call<VisibilityJSON> mVisibilityCall;
    private Call<WindJSON> mWindCall;
    private Call<SatelliteTypeJSON> mSatelliteTypeCall;
    private Call<SatelliteJSON> mSatelliteCall;

    private ImageView mBgImageView;
    private MyTimerTask mMyTimerTask;
    private Timer mTimer;

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
        mSearchHotCityRetrofit = new Retrofit.Builder()
                .baseUrl(com.github.xiaogegechen.weather.Constants.WEATHER_SEARCH_CITY_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void detach() {
        mWeatherFragmentView = null;
        RetrofitHelper.cancelCalls(
                mDayPicCall,
                mHotCityCall,
                mPrecipitationCall,
                mTemperatureCall,
                mVisibilityCall,
                mWindCall,
                mSatelliteTypeCall,
                mSatelliteCall
        );
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void gotoManageCityActivity(List<SelectedCityForRvInMCAct> selectedCityForRvInMCActList) {
        Intent intent = new Intent(mActivity, ManageCityActivity.class);
        if (selectedCityForRvInMCActList != null) {
            SelectedCityForRvInMCAct[] data = new SelectedCityForRvInMCAct[selectedCityForRvInMCActList.size()];
            for (int i = 0; i < selectedCityForRvInMCActList.size(); i++) {
                data[i] = selectedCityForRvInMCActList.get(i);
            }
            intent.putExtra(
                    com.github.xiaogegechen.weather.Constants.INTENT_PARAM_FROM_WEATHER_FRAGMENT_TO_MANAGE_CITY_ACTIVITY,
                    data);
        }
        mActivity.startActivity(intent);
    }

    @Override
    public void gotoManageCityActivityIfNeeded() {
        List<SelectedCity> selectedCities = SelectedCitiesManager.getInstance().getSelectedCities();
        if(selectedCities == null || selectedCities.size() == 0){
            // 没有添加任何城市，先添加城市
            gotoManageCityActivity(null);
        }else{
            // 批量添加进viewPager
            mWeatherFragmentView.addCityList2ViewPager(convertSelectedCityInDBList2CityInfoList(selectedCities));
        }
    }

    @Override
    public void gotoSelectedCityActivity() {
        Intent[] intents = new Intent[]{
                new Intent(mActivity, ManageCityActivity.class),
                new Intent(mActivity, SelectCityActivity.class)
        };
        mActivity.startActivities(intents);
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
                t.printStackTrace();
                logError("query day pic, failed response, exception -> " + t.getMessage());
            }
        });
    }

    @Override
    public void gotoSetting() {
        Intent intent = new Intent(mActivity, SettingActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    public void queryBannerViewContent() {
        queryPrecipitation();
        queryTemperature();
        queryVisibility();
        queryWind();
        querySatellite();
    }

    @Override
    public void queryHotCity() {
        mHotCityCall = mSearchHotCityRetrofit.create(Api.class).queryTopCityList(
                com.github.xiaogegechen.weather.Constants.WEATHER_KEY, "cn");
        mHotCityCall.enqueue(new Callback<TopCityJSON>() {
            @Override
            public void onResponse(@NotNull Call<TopCityJSON> call, @NotNull Response<TopCityJSON> response) {
                TopCityJSON body = response.body();
                if (body != null && isTopCityJSONOK(body)) {
                    List<String> cityList = convertTopCityJSON2CityList(body);
                    mWeatherFragmentView.showHotCities(cityList);
                }else{
                    logError("fail query hot city, body is null or failed check");
                }
            }

            @Override
            public void onFailure(@NotNull Call<TopCityJSON> call, @NotNull Throwable t) {
                t.printStackTrace();
                logError("query hot city, failed response, exception -> " + t.getMessage());
            }
        });
    }

    private void queryPrecipitation(){
        mPrecipitationCall = mMyServerRetrofit.create(Api.class).queryPrecipitation();
        mPrecipitationCall.enqueue(new MyCallback<>(IWeatherFragmentView.TYPE_PRECIPITATION));
    }

    private void queryTemperature(){
        mTemperatureCall = mMyServerRetrofit.create(Api.class).queryTemperature();
        mTemperatureCall.enqueue(new MyCallback<>(IWeatherFragmentView.TYPE_TEMPERATURE));
    }

    private void queryVisibility(){
        mVisibilityCall = mMyServerRetrofit.create(Api.class).queryVisibility();
        mVisibilityCall.enqueue(new MyCallback<>(IWeatherFragmentView.TYPE_VISIBILITY));
    }

    private void queryWind(){
        mWindCall = mMyServerRetrofit.create(Api.class).queryWind();
        mWindCall.enqueue(new MyCallback<>(IWeatherFragmentView.TYPE_WIND));
    }

    private void querySatellite(){
        // 先请求可选的类型
        mSatelliteTypeCall = mMyServerRetrofit.create(Api.class).querySatelliteType();
        mSatelliteTypeCall.enqueue(new Callback<SatelliteTypeJSON>() {
            @Override
            public void onResponse(@NotNull Call<SatelliteTypeJSON> call, @NotNull Response<SatelliteTypeJSON> response) {
                if(!call.isCanceled()){
                    final SatelliteTypeJSON body = response.body();
                    if (body != null) {
                        CheckHelper.checkResultFromMyServer(body, new com.github.xiaogegechen.common.network.Callback() {
                            @Override
                            public void onSuccess() {
                                List<SatelliteTypeJSON.Result.Type> typeList = body.getResult().getTypeList();
                                if(typeList.size() > 0){
                                    String firstTypeNum = typeList.get(0).getTypeNum();
                                    // 请求第一张卫星图
                                    mSatelliteCall = mMyServerRetrofit.create(Api.class).querySatellite(firstTypeNum);
                                    mSatelliteCall.enqueue(new MyCallback<>(IWeatherFragmentView.TYPE_SATELLITE));
                                }
                            }

                            @Override
                            public void onFailure(String errorCode, String errorMessage) {
                                logError("querySatellite, response succeed but failed check, errorCode -> " + errorCode + ", errorMessage -> " + errorMessage);
                                mWeatherFragmentView.showToast(errorMessage);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SatelliteTypeJSON> call, @NotNull Throwable t) {
                t.printStackTrace();
                logError("querySatellite, failed response, exception -> " + t.getMessage());
            }
        });
    }

    private class MyCallback<T extends BaseWeatherPicJSON> implements Callback<T>{
        @IWeatherFragmentView.BannerItemType
        private int mType;

        MyCallback(@IWeatherFragmentView.BannerItemType int type) {
            mType = type;
        }

        @Override
        public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
            if(!call.isCanceled()){
                final BaseWeatherPicJSON body = response.body();
                if (body != null) {
                    CheckHelper.checkResultFromMyServer(body, new com.github.xiaogegechen.common.network.Callback() {
                        @Override
                        public void onSuccess() {
                            List<BaseWeatherPicJSON.Pic> picList = body.getResult().getPicList();
                            // 取第一张的缩略图url显示
                            String compressUrl = picList.get(0).getCompressUrl();
                            mWeatherFragmentView.addPicToBannerView(compressUrl, mType);
                        }

                        @Override
                        public void onFailure(String errorCode, String errorMessage) {
                            logError("query per or tem or vis or wind, response succeed but failed check, errorCode -> " + errorCode + ", errorMessage -> " + errorMessage);
                            mWeatherFragmentView.showToast(errorMessage);
                        }
                    });
                }
            }
        }

        @Override
        public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
            t.printStackTrace();
            logError("query per or tem or vis or wind, failed response, exception -> " + t.getMessage());
        }
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
            if(!BooleanValueManager.getInstance().isAllowBgChange()){
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

    // 响应的数据是否有效
    private static boolean isTopCityJSONOK(TopCityJSON topCityJSON){
        return "ok".equals(topCityJSON.getResult().get(0).getStatus());
    }

    private static List<String> convertTopCityJSON2CityList(TopCityJSON topCityJSON){
        List<String> result = new ArrayList<>();
        for (CityInfo cityInfo : topCityJSON.getResult().get(0).getCityInfoList()) {
            result.add(cityInfo.getLocation());
        }
        return result;
    }

    private static List<CityInfo> convertSelectedCityInDBList2CityInfoList(List<SelectedCity> selectedCities){
        List<CityInfo> result = new ArrayList<>();
        for (SelectedCity selectedCity : selectedCities) {
            CityInfo cityInfo = new CityInfo();
            cityInfo.setAdminArea(selectedCity.getAdminArea());
            cityInfo.setCityId(selectedCity.getCityId());
            cityInfo.setCountry(selectedCity.getCountry());
            cityInfo.setLatitude(selectedCity.getLatitude());
            cityInfo.setLocation(selectedCity.getLocation());
            cityInfo.setLongitude(selectedCity.getLongitude());
            cityInfo.setParentCity(selectedCity.getParentCity());
            cityInfo.setTimeZone(selectedCity.getTimeZone());
            cityInfo.setType(selectedCity.getType());
            result.add(cityInfo);
        }
        return result;
    }

    private static void logError(String message){
        LogUtil.e(TAG, message);
    }
}
