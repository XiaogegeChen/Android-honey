package com.github.xiaogegechen.module_left.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherHourlyJSON {
    @SerializedName("HeWeather6")
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {
        /**
         * basic : {"cid":"CN101070101","location":"沈阳","parent_city":"沈阳","admin_area":"辽宁","cnty":"中国","lat":"41.79676819","lon":"123.42909241","tz":"+8.00"}
         * update : {"loc":"2019-11-22 22:56","utc":"2019-11-22 14:56"}
         * status : ok
         * hourly : [{"cloud":"89","cond_code":"104","cond_txt":"阴","dew":"4","hum":"89","pop":"43","pres":"1023","time":"2019-11-23 01:00","tmp":"10","wind_deg":"185","wind_dir":"南风","wind_sc":"5-6","wind_spd":"38"},{"cloud":"94","cond_code":"305","cond_txt":"小雨","dew":"4","hum":"88","pop":"55","pres":"1020","time":"2019-11-23 04:00","tmp":"11","wind_deg":"181","wind_dir":"南风","wind_sc":"5-6","wind_spd":"38"},{"cloud":"99","cond_code":"305","cond_txt":"小雨","dew":"5","hum":"90","pop":"64","pres":"1021","time":"2019-11-23 07:00","tmp":"9","wind_deg":"181","wind_dir":"南风","wind_sc":"4-5","wind_spd":"32"},{"cloud":"100","cond_code":"305","cond_txt":"小雨","dew":"4","hum":"90","pop":"59","pres":"1022","time":"2019-11-23 10:00","tmp":"9","wind_deg":"281","wind_dir":"西北风","wind_sc":"1-2","wind_spd":"11"},{"cloud":"100","cond_code":"305","cond_txt":"小雨","dew":"0","hum":"88","pop":"16","pres":"500","time":"2019-11-23 13:00","tmp":"8","wind_deg":"182","wind_dir":"南风","wind_sc":"3-4","wind_spd":"19"},{"cloud":"100","cond_code":"305","cond_txt":"小雨","dew":"-5","hum":"85","pop":"16","pres":"1024","time":"2019-11-23 16:00","tmp":"6","wind_deg":"184","wind_dir":"南风","wind_sc":"4-5","wind_spd":"30"},{"cloud":"99","cond_code":"305","cond_txt":"小雨","dew":"-7","hum":"84","pop":"7","pres":"1025","time":"2019-11-23 19:00","tmp":"3","wind_deg":"312","wind_dir":"西北风","wind_sc":"4-5","wind_spd":"28"},{"cloud":"98","cond_code":"101","cond_txt":"多云","dew":"-9","hum":"84","pop":"7","pres":"1022","time":"2019-11-23 22:00","tmp":"-2","wind_deg":"-1","wind_dir":"无持续风向","wind_sc":"3-4","wind_spd":"18"}]
         */

        private Basic basic;
        private Update update;
        private String status;
        private List<Hourly> hourly;

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

        public List<Hourly> getHourly() {
            return hourly;
        }

        public void setHourly(List<Hourly> hourly) {
            this.hourly = hourly;
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
             * loc : 2019-11-22 22:56
             * utc : 2019-11-22 14:56
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

        public static class Hourly {
            /**
             * cloud : 89
             * cond_code : 104
             * cond_txt : 阴
             * dew : 4
             * hum : 89
             * pop : 43
             * pres : 1023
             * time : 2019-11-23 01:00
             * tmp : 10
             * wind_deg : 185
             * wind_dir : 南风
             * wind_sc : 5-6
             * wind_spd : 38
             */

            private String cloud;
            @SerializedName("cond_code")
            private String condCode;
            @SerializedName("cond_txt")
            private String condDescription;
            @SerializedName("dew")
            private String dewPointTemp;
            @SerializedName("hum")
            private String humitidy;
            @SerializedName("pop")
            private String precipitationProbability;
            @SerializedName("pres")
            private String pressure;
            private String time;
            private String tmp;
            @SerializedName("wind_deg")
            private String windDegree;
            @SerializedName("wind_dir")
            private String windDirection;
            @SerializedName("wind_sc")
            private String windPower;
            @SerializedName("wind_spd")
            private String windSpeed;

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getCondCode() {
                return condCode;
            }

            public void setCondCode(String condCode) {
                this.condCode = condCode;
            }

            public String getCondDescription() {
                return condDescription;
            }

            public void setCondDescription(String condDescription) {
                this.condDescription = condDescription;
            }

            public String getDewPointTemp() {
                return dewPointTemp;
            }

            public void setDewPointTemp(String dewPointTemp) {
                this.dewPointTemp = dewPointTemp;
            }

            public String getHumitidy() {
                return humitidy;
            }

            public void setHumitidy(String humitidy) {
                this.humitidy = humitidy;
            }

            public String getPrecipitationProbability() {
                return precipitationProbability;
            }

            public void setPrecipitationProbability(String precipitationProbability) {
                this.precipitationProbability = precipitationProbability;
            }

            public String getPressure() {
                return pressure;
            }

            public void setPressure(String pressure) {
                this.pressure = pressure;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getWindDegree() {
                return windDegree;
            }

            public void setWindDegree(String windDegree) {
                this.windDegree = windDegree;
            }

            public String getWindDirection() {
                return windDirection;
            }

            public void setWindDirection(String windDirection) {
                this.windDirection = windDirection;
            }

            public String getWindPower() {
                return windPower;
            }

            public void setWindPower(String windPower) {
                this.windPower = windPower;
            }

            public String getWindSpeed() {
                return windSpeed;
            }

            public void setWindSpeed(String windSpeed) {
                this.windSpeed = windSpeed;
            }
        }
    }
}
