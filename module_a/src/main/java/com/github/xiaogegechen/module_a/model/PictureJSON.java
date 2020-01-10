package com.github.xiaogegechen.module_a.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 查询图片列表的json格式
 */
public class PictureJSON {

    // 错误码，0位成功，其它是失败
    @SerializedName("error_code")
    private String mErrorCode;

    // 错误信息
    @SerializedName("error_message")
    private String mErrorMessage;

    // 返回的结果
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
        // 改类型图片在服务器里的总数
        @SerializedName("type_count")
        private String mTotalCount;

        // 实际返回的图片数量
        @SerializedName("return_count")
        private String mCount;

        // type，取值为0，1，2
        @SerializedName("type")
        private String mType;

        // offset，起始位置下标
        @SerializedName("offset")
        private String mOffset;

        // 图片url
        @SerializedName("url_list")
        private List<Picture> mPictureList;

        public String getTotalCount() {
            return mTotalCount;
        }

        public void setTotalCount(String totalCount) {
            mTotalCount = totalCount;
        }

        public String getCount() {
            return mCount;
        }

        public void setCount(String count) {
            mCount = count;
        }

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

        public List<Picture> getPictureList() {
            return mPictureList;
        }

        public void setPictureList(List<Picture> pictureList) {
            mPictureList = pictureList;
        }
    }

    public static class Picture{
        // 大图片url
        @SerializedName("r_url")
        private String mRealUrl;
        // 大图片宽度
        @SerializedName("r_w")
        private String mRealImageWidth;
        // 大图片高度
        @SerializedName("r_h")
        private String mRealImageHeight;
        // 大图片文件大小
        @SerializedName("r_f_s")
        private String mRealImageFileSize;
        // 小图片url
        @SerializedName("c_url")
        private String mCompressUrl;
        // 小图片宽度
        @SerializedName("c_w")
        private String mCompressImageWidth;
        // 小图片高度
        @SerializedName("c_h")
        private String mCompressImageHeight;
        // 小图片文件大小
        @SerializedName("c_f_s")
        private String mCompressImageFileSize;

        public String getRealUrl() {
            return mRealUrl;
        }

        public void setRealUrl(String realUrl) {
            mRealUrl = realUrl;
        }

        public String getRealImageWidth() {
            return mRealImageWidth;
        }

        public void setRealImageWidth(String realImageWidth) {
            mRealImageWidth = realImageWidth;
        }

        public String getRealImageHeight() {
            return mRealImageHeight;
        }

        public void setRealImageHeight(String realImageHeight) {
            mRealImageHeight = realImageHeight;
        }

        public String getRealImageFileSize() {
            return mRealImageFileSize;
        }

        public void setRealImageFileSize(String realImageFileSize) {
            mRealImageFileSize = realImageFileSize;
        }

        public String getCompressUrl() {
            return mCompressUrl;
        }

        public void setCompressUrl(String compressUrl) {
            mCompressUrl = compressUrl;
        }

        public String getCompressImageWidth() {
            return mCompressImageWidth;
        }

        public void setCompressImageWidth(String compressImageWidth) {
            mCompressImageWidth = compressImageWidth;
        }

        public String getCompressImageHeight() {
            return mCompressImageHeight;
        }

        public void setCompressImageHeight(String compressImageHeight) {
            mCompressImageHeight = compressImageHeight;
        }

        public String getCompressImageFileSize() {
            return mCompressImageFileSize;
        }

        public void setCompressImageFileSize(String compressImageFileSize) {
            mCompressImageFileSize = compressImageFileSize;
        }
    }
}
