package com.github.xiaogegechen.weather.model.json;

import com.github.xiaogegechen.common.network.MyServerCheckable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseWeatherPicJSON implements MyServerCheckable {
    @SerializedName("error_code")
    protected String mErrorCode;
    @SerializedName("error_message")
    protected String mErrorMessage;
    @SerializedName("result")
    protected Result mResult;

    @Override
    public String errorCode() {
        return mErrorCode;
    }

    @Override
    public String errorMessage() {
        return mErrorMessage;
    }

    public String getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(String errorCode) {
        mErrorCode = errorCode;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        mResult = result;
    }

    public static class Result{
        @SerializedName("count")
        protected String mCount;
        @SerializedName("pic_list")
        protected List<Pic> mPicList;

        public String getCount() {
            return mCount;
        }

        public void setCount(String count) {
            mCount = count;
        }

        public List<Pic> getPicList() {
            return mPicList;
        }

        public void setPicList(List<Pic> picList) {
            mPicList = picList;
        }
    }

    public static class Pic{
        @SerializedName("time")
        protected String mTime;
        @SerializedName("c_url")
        protected String mCompressUrl;
        @SerializedName("r_url")
        protected String mRealUrl;

        public String getTime() {
            return mTime;
        }

        public void setTime(String time) {
            mTime = time;
        }

        public String getCompressUrl() {
            return mCompressUrl;
        }

        public void setCompressUrl(String compressUrl) {
            mCompressUrl = compressUrl;
        }

        public String getRealUrl() {
            return mRealUrl;
        }

        public void setRealUrl(String realUrl) {
            mRealUrl = realUrl;
        }
    }
}
