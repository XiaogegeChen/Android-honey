package com.github.xiaogegechen.weather.model.json;

import com.github.xiaogegechen.weather.model.CityInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityListJSON {
    @SerializedName("HeWeather6")
    private List<Basic> result;

    public List<Basic> getResult() {
        return result;
    }

    public void setResult(List<Basic> result) {
        this.result = result;
    }

    public static class Basic{
        // 基础信息
        @SerializedName("basic")
        private List<CityInfo> cityInfoList;

        // 响应状态
        @SerializedName("status")
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<CityInfo> getCityInfoList() {
            return cityInfoList;
        }

        public void setCityInfoList(List<CityInfo> cityInfoList) {
            this.cityInfoList = cityInfoList;
        }
    }
}
