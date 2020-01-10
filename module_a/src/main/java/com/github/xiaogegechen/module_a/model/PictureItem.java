package com.github.xiaogegechen.module_a.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * fragment_a 中的recyclerView 的子项
 */
public class PictureItem implements Parcelable {
    // 大图片url
    private String mRealUrl;
    // 大图片宽度
    private int mRealImageWidth;
    // 大图片高度
    private int mRealImageHeight;
    // 大图片文件大小 kb
    private float mRealImageFileSize;
    // 小图片url
    private String mCompressUrl;
    // 小图片宽度
    private int mCompressImageWidth;
    // 小图片高度
    private int mCompressImageHeight;
    // 小图片文件大小 kb
    private float mCompressImageFileSize;
    // 图片的描述
    private String mDescription;

    public PictureItem(){}

    protected PictureItem(Parcel in) {
        mRealUrl = in.readString();
        mRealImageWidth = in.readInt();
        mRealImageHeight = in.readInt();
        mRealImageFileSize = in.readFloat();
        mCompressUrl = in.readString();
        mCompressImageWidth = in.readInt();
        mCompressImageHeight = in.readInt();
        mCompressImageFileSize = in.readFloat();
        mDescription = in.readString();
    }

    public static final Creator<PictureItem> CREATOR = new Creator<PictureItem>() {
        @Override
        public PictureItem createFromParcel(Parcel in) {
            return new PictureItem(in);
        }

        @Override
        public PictureItem[] newArray(int size) {
            return new PictureItem[size];
        }
    };

    public String getRealUrl() {
        return mRealUrl;
    }

    public void setRealUrl(String realUrl) {
        mRealUrl = realUrl;
    }

    public int getRealImageWidth() {
        return mRealImageWidth;
    }

    public void setRealImageWidth(int realImageWidth) {
        mRealImageWidth = realImageWidth;
    }

    public int getRealImageHeight() {
        return mRealImageHeight;
    }

    public void setRealImageHeight(int realImageHeight) {
        mRealImageHeight = realImageHeight;
    }

    public float getRealImageFileSize() {
        return mRealImageFileSize;
    }

    public void setRealImageFileSize(float realImageFileSize) {
        mRealImageFileSize = realImageFileSize;
    }

    public String getCompressUrl() {
        return mCompressUrl;
    }

    public void setCompressUrl(String compressUrl) {
        mCompressUrl = compressUrl;
    }

    public int getCompressImageWidth() {
        return mCompressImageWidth;
    }

    public void setCompressImageWidth(int compressImageWidth) {
        mCompressImageWidth = compressImageWidth;
    }

    public int getCompressImageHeight() {
        return mCompressImageHeight;
    }

    public void setCompressImageHeight(int compressImageHeight) {
        mCompressImageHeight = compressImageHeight;
    }

    public float getCompressImageFileSize() {
        return mCompressImageFileSize;
    }

    public void setCompressImageFileSize(float compressImageFileSize) {
        mCompressImageFileSize = compressImageFileSize;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRealUrl);
        dest.writeInt(mRealImageWidth);
        dest.writeInt(mRealImageHeight);
        dest.writeFloat(mRealImageFileSize);
        dest.writeString(mCompressUrl);
        dest.writeInt(mCompressImageWidth);
        dest.writeInt(mCompressImageHeight);
        dest.writeFloat(mCompressImageFileSize);
        dest.writeString(mDescription);
    }
}
