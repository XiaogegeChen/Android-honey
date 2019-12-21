package com.github.xiaogegechen.bing.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * bing套图中用于描述一个图片的数据结构
 */
public class Image implements Parcelable {
    // 图片所属module
    private String mModuleType;
    // 图片所属topic
    private String mTopicType;
    // 图片缩略图url
    private String mThumbnailUrl;
    // 图片大图url
    private String mRealUrl;
    // 图片来源网站连接
    private String mFromUrl;

    public Image(){}

    protected Image(Parcel in) {
        mModuleType = in.readString();
        mTopicType = in.readString();
        mThumbnailUrl = in.readString();
        mRealUrl = in.readString();
        mFromUrl = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getModuleType() {
        return mModuleType;
    }

    public void setModuleType(String moduleType) {
        mModuleType = moduleType;
    }

    public String getTopicType() {
        return mTopicType;
    }

    public void setTopicType(String topicType) {
        mTopicType = topicType;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }

    public String getRealUrl() {
        return mRealUrl;
    }

    public void setRealUrl(String realUrl) {
        mRealUrl = realUrl;
    }

    public String getFromUrl() {
        return mFromUrl;
    }

    public void setFromUrl(String fromUrl) {
        mFromUrl = fromUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mModuleType);
        dest.writeString(mTopicType);
        dest.writeString(mThumbnailUrl);
        dest.writeString(mRealUrl);
        dest.writeString(mFromUrl);
    }
}
