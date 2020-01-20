package com.github.xiaogegechen.weather.model.json;

import com.github.xiaogegechen.common.network.Checkable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayPicJSON implements Checkable {
    private Result result;
    @SerializedName("error_code")
    private String errorCode;
    @SerializedName("error_message")
    private String errorMessage;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String errorCode() {
        return this.errorCode;
    }

    @Override
    public String errorMessage() {
        return this.errorMessage;
    }

    public static class Result {
        /**
         * count : 1
         * pic_list : [{"tm":"2020-01-19","url":"http://cn.bing.com/th?id=OHR.SpeedFlying_ROW1344290466_1920x1080.jpg&rf=LaDigue_1920x1081920x1080.jpg"}]
         */

        private String count;
        @SerializedName("pic_list")
        private List<PicList> picList;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<PicList> getPicList() {
            return picList;
        }

        public void setPicList(List<PicList> picList) {
            this.picList = picList;
        }

        public static class PicList {
            /**
             * tm : 2020-01-19
             * url : http://cn.bing.com/th?id=OHR.SpeedFlying_ROW1344290466_1920x1080.jpg&rf=LaDigue_1920x1081920x1080.jpg
             */

            @SerializedName("tm")
            private String time;
            private String url;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
