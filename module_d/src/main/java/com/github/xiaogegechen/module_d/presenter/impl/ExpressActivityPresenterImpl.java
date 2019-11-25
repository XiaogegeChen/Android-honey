package com.github.xiaogegechen.module_d.presenter.impl;

import android.content.Context;
import android.graphics.Color;

import com.github.xiaogegechen.LogInterceptor;
import com.github.xiaogegechen.common.util.RetrofitHelper;
import com.github.xiaogegechen.module_d.Api;
import com.github.xiaogegechen.module_d.Constants;
import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.model.json.ExpressJSON;
import com.github.xiaogegechen.module_d.model.ExpressMotionItem;
import com.github.xiaogegechen.module_d.presenter.IExpressActivityPresenter;
import com.github.xiaogegechen.module_d.view.IExpressActivityView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
        mExpressActivityView.showProgress();
        mExpressCall = mExpressRetrofit.create(Api.class).queryExpress(expressNumber);
        mExpressCall.enqueue(new Callback<ExpressJSON>() {
            @Override
            public void onResponse(@NotNull Call<ExpressJSON> call, @NotNull Response<ExpressJSON> response) {
                ExpressJSON expressJSON = response.body();
                if(expressJSON != null){
                    if(isStatusOK(expressJSON)){
                        mExpressActivityView.showInformation(expressJSON);
                    }else{
                        // 单号不对
                        mExpressActivityView.showErrorPage(convertStatusCode2String(expressJSON));
                    }
                }else{
                    // 未知错误
                    mExpressActivityView.showErrorPage(Constants.UNKNOWN_ERROR);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ExpressJSON> call, @NotNull Throwable t) {
                t.printStackTrace();
                if(!call.isCanceled()){
                    mExpressActivityView.showErrorPage(t.toString());
                }
            }
        });
    }

    // 运送过程中
    private static final int TYPE_MOVING = 100;
    // 失败
    private static final int TYPE_ERROR = 101;
    // 已签收
    private static final int TYPE_OK = 102;

    @Override
    public List<ExpressMotionItem> convertExpressJSON2ItemList(ExpressJSON expressJSON) {
        List<ExpressMotionItem> result = new ArrayList<>();
        List<ExpressJSON.MotionTrack> motionTrackList = expressJSON.getResult().getMotionTrackList();
        int statusCode = 0;
        int iconType = TYPE_MOVING;
        try{
            statusCode = Integer.parseInt(expressJSON.getResult().getDeliveryStatus());
        }catch (Exception e){
            e.printStackTrace();
        }
        switch (statusCode){
            case 3:
                iconType = TYPE_OK;
                break;
            case 4:
            case 5:
            case 6:
                iconType = TYPE_ERROR;
                break;
            default:
                break;
        }
        if(motionTrackList != null && motionTrackList.size() > 0){
            for (int i = 0; i < motionTrackList.size(); i++) {
                String time = "";
                String exactTime = "";
                int iconId;
                ExpressJSON.MotionTrack motionTrack = motionTrackList.get(i);
                try{
                    // 拿到的格式是 2018-03-08 21:00:44，时间取 03-08，精确时间取 21:00
                    time = motionTrack.getTime().split(" ")[0].substring(5);
                    exactTime = motionTrack.getTime().split(" ")[1].substring(0, 5);
                }catch (Exception e){
                    e.printStackTrace();
                }
                ExpressMotionItem expressMotionItem = new ExpressMotionItem();
                // 第一个是最新的
                if (i == 0){
                    expressMotionItem.setColor(Color.BLACK);
                    expressMotionItem.setSplitColor(Color.BLACK);
                    switch (iconType){
                        case TYPE_MOVING:
                            iconId = R.drawable.module_d_ic_express_motion_moving;
                            break;
                        case TYPE_OK:
                            iconId = R.drawable.module_d_ic_express_motion_ok;
                            break;
                        case TYPE_ERROR:
                            iconId = R.drawable.module_d_ic_express_motion_failed;
                            break;
                        default:
                            iconId = R.drawable.module_d_ic_express_motion_failed;
                            break;
                    }
                }else{
                    int color = ((Context) mExpressActivityView).getResources().getColor(R.color.module_d_activity_express_item_color_pass);
                    expressMotionItem.setColor(color);
                    iconId = R.drawable.module_d_ic_express_motion_pass;
                    // 最后一个分割线不可见
                    if(i == motionTrackList.size() - 1){
                        expressMotionItem.setSplitColor(Color.TRANSPARENT);
                    }else{
                        expressMotionItem.setSplitColor(color);
                    }
                }
                expressMotionItem.setIconId(iconId);
                expressMotionItem.setTime(time);
                expressMotionItem.setExactTime(exactTime);
                expressMotionItem.setInfo(motionTrack.getStatus());
                result.add(expressMotionItem);
            }
        }
        return result;
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

    /**
     * 把状态码转化为文字描述
     * @param expressJSON expressJSON
     * @return 相应的文字描述
     */
    private static String convertStatusCode2String(ExpressJSON expressJSON){
        switch (expressJSON.getStatusCode()){
            case "0":
                return "正常查询";
            case "201":
                return "快递单号错误";
            case "203":
                return "快递公司不存在";
            case "204":
                return "快递公司识别失败";
            case "205":
                return "没有信息";
            case "207":
                return "该单号被限制，错误单号";
            default:
                return Constants.UNKNOWN_ERROR;
        }
    }

    /**
     * 判断响应是否成功
     * @param expressJSON expressJSON
     * @return true 如果响应成功
     */
    private static boolean isStatusOK(ExpressJSON expressJSON){
        return "0".equals(expressJSON.getStatusCode());
    }
}
