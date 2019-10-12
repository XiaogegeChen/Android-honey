package com.github.xiaogegechen.module_d.presenter.impl;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.module_d.Api;
import com.github.xiaogegechen.module_d.Constants;
import com.github.xiaogegechen.module_d.model.ExpressJSON;
import com.github.xiaogegechen.module_d.presenter.IExpressActivityPresenter;
import com.github.xiaogegechen.module_d.view.IExpressActivityView;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExpressActivityPresenterImpl implements IExpressActivityPresenter {

    private IExpressActivityView mExpressActivityView;

    private Retrofit mExpressRetrofit;
    private Call<ExpressJSON> mExpressCall;

    public ExpressActivityPresenterImpl(){
        mExpressRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.EXPRESS_BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new LogInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void queryExpressMessage(String expressNumber) {
        mExpressCall = mExpressRetrofit.create(Api.class).queryExpress(expressNumber);
        mExpressCall.enqueue(new Callback<ExpressJSON>() {
            @Override
            public void onResponse(@NotNull Call<ExpressJSON> call, @NotNull Response<ExpressJSON> response) {

            }

            @Override
            public void onFailure(@NotNull Call<ExpressJSON> call, @NotNull Throwable t) {
                if(!call.isCanceled()){

                }
            }
        });
    }

    @Override
    public void attach(IExpressActivityView expressActivityView) {
        mExpressActivityView = expressActivityView;
    }

    @Override
    public void detach() {
        RetrofitHelper.cancelCalls(mExpressCall);
        mExpressActivityView = null;
    }
}
