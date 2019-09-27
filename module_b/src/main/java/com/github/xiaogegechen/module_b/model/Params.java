package com.github.xiaogegechen.module_b.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 跳转到{@link com.github.xiaogegechen.module_b.view.ConstellationDetailActivity}
 * 时携带的参数，json格式
 */
public class Params implements Parcelable {
    private String consName;
    private int iconId;

    public Params(String consName, int iconId) {
        this.consName = consName;
        this.iconId = iconId;
    }

    private Params(Parcel in) {
        consName = in.readString();
        iconId = in.readInt();
    }

    public static final Creator<Params> CREATOR = new Creator<Params>() {
        @Override
        public Params createFromParcel(Parcel in) {
            return new Params(in);
        }

        @Override
        public Params[] newArray(int size) {
            return new Params[size];
        }
    };

    public String getConsName() {
        return consName;
    }

    public int getIconId() {
        return iconId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(consName);
        dest.writeInt(iconId);
    }
}
