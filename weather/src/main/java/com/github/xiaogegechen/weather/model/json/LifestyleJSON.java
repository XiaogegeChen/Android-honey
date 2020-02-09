package com.github.xiaogegechen.weather.model.json;

import com.github.xiaogegechen.common.network.HWeatherServerCheckable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LifestyleJSON implements HWeatherServerCheckable {
    @SerializedName("HeWeather6")
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    @Override
    public String errorMessage() {
        return result.get(0).status;
    }

    public static class Result {
        /**
         * basic : {"cid":"CN101070101","location":"沈阳","parent_city":"沈阳","admin_area":"辽宁","cnty":"中国","lat":"41.79676819","lon":"123.42909241","tz":"+8.00"}
         * update : {"loc":"2019-11-23 11:55","utc":"2019-11-23 03:55"}
         * status : ok
         * lifestyle : [{"type":"comf","brf":"很不舒适","txt":"白天天气凉、风力较强，在这种天气条件下，您会感觉很冷，不舒适，外出不宜过久，并注意防风保暖。"},{"type":"drsg","brf":"冷","txt":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。"},{"type":"flu","brf":"极易发","txt":"昼夜温差极大，且风力较强，极易发生感冒，请特别注意增减衣服保暖防寒。"},{"type":"sport","brf":"较不宜","txt":"有降水，且风力较强，较适宜在户内进行各种健身休闲运动；若坚持户外运动，请注意保暖。"},{"type":"trav","brf":"一般","txt":"温度适宜，但风稍大，且较强降雨的天气将给您的出行带来很多的不便，若坚持旅行建议带上雨具。"},{"type":"uv","brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"},{"type":"cw","brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},{"type":"air","brf":"优","txt":"气象条件非常有利于空气污染物稀释、扩散和清除。"}]
         */

        private Basic basic;
        private Update update;
        private String status;
        private List<Lifestyle> lifestyle;

        public Basic getBasic() {
            return basic;
        }

        public void setBasic(Basic basic) {
            this.basic = basic;
        }

        public Update getUpdate() {
            return update;
        }

        public void setUpdate(Update update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<Lifestyle> getLifestyle() {
            return lifestyle;
        }

        public void setLifestyle(List<Lifestyle> lifestyle) {
            this.lifestyle = lifestyle;
        }

        public static class Basic {
            /**
             * cid : CN101070101
             * location : 沈阳
             * parent_city : 沈阳
             * admin_area : 辽宁
             * cnty : 中国
             * lat : 41.79676819
             * lon : 123.42909241
             * tz : +8.00
             */

            @SerializedName("cid")
            private String cityId;
            private String location;
            @SerializedName("parent_city")
            private String parentCity;
            @SerializedName("admin_area")
            private String adminArea;
            @SerializedName("cnty")
            private String country;
            @SerializedName("lat")
            private String latitude;
            @SerializedName("lon")
            private String longitude;
            @SerializedName("tz")
            private String timeZone;

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParentCity() {
                return parentCity;
            }

            public void setParentCity(String parentCity) {
                this.parentCity = parentCity;
            }

            public String getAdminArea() {
                return adminArea;
            }

            public void setAdminArea(String adminArea) {
                this.adminArea = adminArea;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getTimeZone() {
                return timeZone;
            }

            public void setTimeZone(String timeZone) {
                this.timeZone = timeZone;
            }
        }

        public static class Update {
            /**
             * loc : 2019-11-23 11:55
             * utc : 2019-11-23 03:55
             */

            @SerializedName("loc")
            private String localTime;
            @SerializedName("utc")
            private String utcTime;

            public String getLocalTime() {
                return localTime;
            }

            public void setLocalTime(String localTime) {
                this.localTime = localTime;
            }

            public String getUtcTime() {
                return utcTime;
            }

            public void setUtcTime(String utcTime) {
                this.utcTime = utcTime;
            }
        }

        public static class Lifestyle {
            /**
             * type : comf
             * brf : 很不舒适
             * txt : 白天天气凉、风力较强，在这种天气条件下，您会感觉很冷，不舒适，外出不宜过久，并注意防风保暖。
             */

            private String type;
            @SerializedName("brf")
            private String briefDescription;
            @SerializedName("txt")
            private String description;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getBriefDescription() {
                return briefDescription;
            }

            public void setBriefDescription(String briefDescription) {
                this.briefDescription = briefDescription;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
