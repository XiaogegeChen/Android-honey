package com.xiaogege.honey.ui;

import com.google.gson.annotations.SerializedName;

public class MojieToday {
    @SerializedName ("datetime")
    private String data;

    @SerializedName ("all")
    private String all;

    @SerializedName ("color")
    private String color;

    @SerializedName ("health")
    private String health;

    @SerializedName ("love")
    private String love;

    @SerializedName ("money")
    private String money;

    @SerializedName ("number")
    private String number;

    @SerializedName ("QFriend")
    private String qFriend;

    @SerializedName ("summary")
    private String summary;

    @SerializedName ("work")
    private String work;

    @SerializedName ("resultcode")
    private String resultCode;

    @SerializedName ("error_code")
    private String errorCode;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getqFriend() {
        return qFriend;
    }

    public void setqFriend(String qFriend) {
        this.qFriend = qFriend;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

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
}
