package com.github.xiaogegechen.module_d.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 图书列表的JSON数据
 */
public class BookListJSON {

    @SerializedName("resultcode")
    private String resultCode;

    private String reason;

    private Result result;

    @SerializedName("error_code")
    private int errorCode;

    public static class Result{
        private List<Data> data;
        private String totalNum;
        private String pn;
        private String rn;

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public String getPn() {
            return pn;
        }

        public void setPn(String pn) {
            this.pn = pn;
        }

        public String getRn() {
            return rn;
        }

        public void setRn(String rn) {
            this.rn = rn;
        }
    }

    public static class Data{
        private String title;
        private String catalog;
        private String tags;
        private String sub1;
        private String sub2;
        private String img;
        private String reading;
        private String online;
        private String bytime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getSub1() {
            return sub1;
        }

        public void setSub1(String sub1) {
            this.sub1 = sub1;
        }

        public String getSub2() {
            return sub2;
        }

        public void setSub2(String sub2) {
            this.sub2 = sub2;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getReading() {
            return reading;
        }

        public void setReading(String reading) {
            this.reading = reading;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        public String getBytime() {
            return bytime;
        }

        public void setBytime(String bytime) {
            this.bytime = bytime;
        }
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
