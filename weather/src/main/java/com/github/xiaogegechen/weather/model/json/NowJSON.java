package com.github.xiaogegechen.weather.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NowJSON {

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
         * update : {"loc":"2019-11-22 20:42","utc":"2019-11-22 12:42"}
         * status : ok
         * now : {"cloud":"91","cond_code":"104","cond_txt":"阴","fl":"8","hum":"73","pcpn":"0.0","pres":"1014","tmp":"9","vis":"16","wind_deg":"175","wind_dir":"南风","wind_sc":"1","wind_spd":"2"}
         */

        private Basic basic;
        private UpdateTime update;
        private String status;
        private Now now;

        public Basic getBasic() {
            return basic;
        }

        public void setBasic(Basic basic) {
            this.basic = basic;
        }

        public UpdateTime getUpdate() {
            return update;
        }

        public void setUpdate(UpdateTime update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Now getNow() {
            return now;
        }

        public void setNow(Now now) {
            this.now = now;
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

        public static class UpdateTime {
            /**
             * loc : 2019-11-22 20:42
             * utc : 2019-11-22 12:42
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

        public static class Now {
            /**
             * cloud : 91
             * cond_code : 104
             * cond_txt : 阴
             * fl : 8
             * hum : 73
             * pcpn : 0.0
             * pres : 1014
             * tmp : 9
             * vis : 16
             * wind_deg : 175
             * wind_dir : 南风
             * wind_sc : 1
             * wind_spd : 2
             */

            private String cloud;
            @SerializedName("cond_code")
            private String condCode;
            @SerializedName("cond_txt")
            private String condDescription;
            @SerializedName("fl")
            private String feelTemp;
            @SerializedName("hum")
            private String humidity;
            @SerializedName("pcpn")
            private String precipitation;
            @SerializedName("pres")
            private String pressure;
            private String tmp;
            @SerializedName("vis")
            private String visibility;
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

            public String getFeelTemp() {
                return feelTemp;
            }

            public void setFeelTemp(String feelTemp) {
                this.feelTemp = feelTemp;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getPrecipitation() {
                return precipitation;
            }

            public void setPrecipitation(String precipitation) {
                this.precipitation = precipitation;
            }

            public String getPressure() {
                return pressure;
            }

            public void setPressure(String pressure) {
                this.pressure = pressure;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVisibility() {
                return visibility;
            }

            public void setVisibility(String visibility) {
                this.visibility = visibility;
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
