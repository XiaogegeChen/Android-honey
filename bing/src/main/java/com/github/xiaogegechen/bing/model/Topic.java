package com.github.xiaogegechen.bing.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * bing套图中用于描述一个topic的数据结构
 */
public class Topic implements Parcelable {
    // 所属module的标题
    private String mModuleTitle;
    // 所属module
    private String mModuleType;
    // 标题
    private String mTitle;
    // 封面url
    private String mCoverUrl;
    // type
    private String mType;
    // 这个topic下所有图片
    private List<Image> mImageList;

    public Topic(){}

    protected Topic(Parcel in) {
        mModuleTitle = in.readString();
        mModuleType = in.readString();
        mTitle = in.readString();
        mCoverUrl = in.readString();
        mType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mModuleTitle);
        dest.writeString(mModuleType);
        dest.writeString(mTitle);
        dest.writeString(mCoverUrl);
        dest.writeString(mType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    public String getModuleTitle() {
        return mModuleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        mModuleTitle = moduleTitle;
    }

    public List<Image> getImageList() {
        return mImageList;
    }

    public void setImageList(List<Image> imageList) {
        mImageList = imageList;
    }

    public String getModuleType() {
        return mModuleType;
    }

    public void setModuleType(String moduleType) {
        mModuleType = moduleType;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        mCoverUrl = coverUrl;
    }
}
