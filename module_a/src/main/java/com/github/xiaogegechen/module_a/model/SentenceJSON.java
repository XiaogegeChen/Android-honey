package com.github.xiaogegechen.module_a.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 查询短句列表的json格式
 */
public class SentenceJSON {

    // 错误码，0位成功，其它是失败
    @SerializedName("error_code")
    private String mErrorCode;

    // 错误信息
    @SerializedName("error_message")
    private String mErrorMessage;

    // 返回的短句列表
    @SerializedName("result")
    private Result mResult;

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
        // type，取值为0，1，2
        @SerializedName("type")
        private String mType;

        // offset，起始位置下标
        @SerializedName("offset")
        private String mOffset;

        // count，返回短句的数量
        @SerializedName("count_return")
        private String mCount;

        // 服务端响应类型资源的总数
        @SerializedName("type_total")
        private String mTypeTotal;

        // 返回的具体内容
        @SerializedName("sent_list")
        private List<Sentence> mSentenceList;

        public String getType() {
            return mType;
        }

        public void setType(String type) {
            mType = type;
        }

        public String getOffset() {
            return mOffset;
        }

        public void setOffset(String offset) {
            mOffset = offset;
        }

        public String getCount() {
            return mCount;
        }

        public void setCount(String count) {
            mCount = count;
        }

        public String getTypeTotal() {
            return mTypeTotal;
        }

        public void setTypeTotal(String typeTotal) {
            mTypeTotal = typeTotal;
        }

        public List<Sentence> getSentenceList() {
            return mSentenceList;
        }

        public void setSentenceList(List<Sentence> sentenceList) {
            mSentenceList = sentenceList;
        }
    }

    public static class Sentence{
        // type，取值为0，1，2
        @SerializedName("type")
        private String mType;

        // 短句的内容
        @SerializedName("content")
        private String mContent;

        public String getType() {
            return mType;
        }

        public void setType(String type) {
            mType = type;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }
    }
}
