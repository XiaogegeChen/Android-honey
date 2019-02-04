package com.xiaogege.honey.ui;

import com.google.gson.annotations.SerializedName;

public class MojieWeek {
    @SerializedName ("date")
    private String data;

    @SerializedName ("love")
    private String love;

    @SerializedName ("money")
    private String money;

    @SerializedName ("work")
    private String work;

    @SerializedName ("weekth")
    private String all;

    @SerializedName ("resultcode")
    private String resultCode;

    @SerializedName ("error_code")
    private String errorCode;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }
}
