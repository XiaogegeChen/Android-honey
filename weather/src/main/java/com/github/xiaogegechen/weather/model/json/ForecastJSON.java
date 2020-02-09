package com.github.xiaogegechen.weather.model.json;

import com.github.xiaogegechen.common.network.HWeatherServerCheckable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastJSON implements HWeatherServerCheckable {

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
         * update : {"loc":"2019-11-23 09:56","utc":"2019-11-23 01:56"}
         * status : ok
         * daily_forecast : [{"cond_code_d":"314","cond_code_n":"300","cond_txt_d":"小到中雨","cond_txt_n":"阵雨","date":"2019-11-23","hum":"42","mr":"02:14","ms":"14:38","pcpn":"5.8","pop":"77","pres":"1022","sr":"06:46","ss":"16:19","tmp_max":"10","tmp_min":"-4","uv_index":"1","vis":"14","wind_deg":"325","wind_dir":"西北风","wind_sc":"4-5","wind_spd":"29"},{"cond_code_d":"101","cond_code_n":"100","cond_txt_d":"多云","cond_txt_n":"晴","date":"2019-11-24","hum":"34","mr":"03:28","ms":"15:08","pcpn":"1.5","pop":"10","pres":"1032","sr":"06:47","ss":"16:18","tmp_max":"-2","tmp_min":"-10","uv_index":"1","vis":"24","wind_deg":"359","wind_dir":"北风","wind_sc":"4-5","wind_spd":"25"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-11-25","hum":"39","mr":"04:41","ms":"15:41","pcpn":"0.0","pop":"0","pres":"1023","sr":"06:48","ss":"16:18","tmp_max":"-2","tmp_min":"-9","uv_index":"2","vis":"25","wind_deg":"189","wind_dir":"南风","wind_sc":"1-2","wind_spd":"10"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2019-11-26","hum":"31","mr":"05:55","ms":"16:16","pcpn":"0.0","pop":"0","pres":"1035","sr":"06:49","ss":"16:17","tmp_max":"4","tmp_min":"-8","uv_index":"2","vis":"25","wind_deg":"196","wind_dir":"西南风","wind_sc":"4-5","wind_spd":"32"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-11-27","hum":"36","mr":"07:06","ms":"16:57","pcpn":"0.0","pop":"0","pres":"1033","sr":"06:50","ss":"16:17","tmp_max":"-3","tmp_min":"-13","uv_index":"2","vis":"25","wind_deg":"10","wind_dir":"北风","wind_sc":"3-4","wind_spd":"18"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-11-28","hum":"27","mr":"08:14","ms":"17:43","pcpn":"0.0","pop":"0","pres":"1030","sr":"06:51","ss":"16:17","tmp_max":"-2","tmp_min":"-11","uv_index":"2","vis":"25","wind_deg":"10","wind_dir":"北风","wind_sc":"1-2","wind_spd":"9"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-11-29","hum":"56","mr":"09:16","ms":"18:35","pcpn":"0.0","pop":"0","pres":"1023","sr":"06:52","ss":"16:16","tmp_max":"0","tmp_min":"-9","uv_index":"2","vis":"11","wind_deg":"181","wind_dir":"南风","wind_sc":"1-2","wind_spd":"10"}]
         */

        private Basic basic;
        private Update update;
        private String status;
        @SerializedName("daily_forecast")
        private List<DailyForecast> dailyForecastList;

        public List<DailyForecast> getDailyForecastList() {
            return dailyForecastList;
        }

        public void setDailyForecastList(List<DailyForecast> dailyForecastList) {
            this.dailyForecastList = dailyForecastList;
        }

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
             * loc : 2019-11-23 09:56
             * utc : 2019-11-23 01:56
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

        public static class DailyForecast {
            /**
             * cond_code_d : 314
             * cond_code_n : 300
             * cond_txt_d : 小到中雨
             * cond_txt_n : 阵雨
             * date : 2019-11-23
             * hum : 42
             * mr : 02:14
             * ms : 14:38
             * pcpn : 5.8
             * pop : 77
             * pres : 1022
             * sr : 06:46
             * ss : 16:19
             * tmp_max : 10
             * tmp_min : -4
             * uv_index : 1
             * vis : 14
             * wind_deg : 325
             * wind_dir : 西北风
             * wind_sc : 4-5
             * wind_spd : 29
             */

            @SerializedName("cond_code_d")
            private String condationCodeOfDay;
            @SerializedName("cond_code_n")
            private String condationCodeOfNight;
            @SerializedName("cond_txt_d")
            private String condationDescriptionOfDay;
            @SerializedName("cond_txt_n")
            private String condationDescriptionOfNight;
            private String date;
            @SerializedName("hum")
            private String humidity;
            @SerializedName("mr")
            private String moonRaiseTime;
            @SerializedName("ms")
            private String moonDownTime;
            @SerializedName("pcpn")
            private String precipitation;
            @SerializedName("pop")
            private String precipitationProbability;
            @SerializedName("pres")
            private String pressure;
            @SerializedName("sr")
            private String sunRaiseTime;
            @SerializedName("ss")
            private String sunDownTime;
            @SerializedName("tmp_max")
            private String tempMax;
            @SerializedName("tmp_min")
            private String tempMin;
            @SerializedName("uv_index")
            private String uvIndex;
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

            public String getCondationCodeOfDay() {
                return condationCodeOfDay;
            }

            public void setCondationCodeOfDay(String condationCodeOfDay) {
                this.condationCodeOfDay = condationCodeOfDay;
            }

            public String getCondationCodeOfNight() {
                return condationCodeOfNight;
            }

            public void setCondationCodeOfNight(String condationCodeOfNight) {
                this.condationCodeOfNight = condationCodeOfNight;
            }

            public String getCondationDescriptionOfDay() {
                return condationDescriptionOfDay;
            }

            public void setCondationDescriptionOfDay(String condationDescriptionOfDay) {
                this.condationDescriptionOfDay = condationDescriptionOfDay;
            }

            public String getCondationDescriptionOfNight() {
                return condationDescriptionOfNight;
            }

            public void setCondationDescriptionOfNight(String condationDescriptionOfNight) {
                this.condationDescriptionOfNight = condationDescriptionOfNight;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getMoonRaiseTime() {
                return moonRaiseTime;
            }

            public void setMoonRaiseTime(String moonRaiseTime) {
                this.moonRaiseTime = moonRaiseTime;
            }

            public String getMoonDownTime() {
                return moonDownTime;
            }

            public void setMoonDownTime(String moonDownTime) {
                this.moonDownTime = moonDownTime;
            }

            public String getPrecipitation() {
                return precipitation;
            }

            public void setPrecipitation(String precipitation) {
                this.precipitation = precipitation;
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

            public String getSunRaiseTime() {
                return sunRaiseTime;
            }

            public void setSunRaiseTime(String sunRaiseTime) {
                this.sunRaiseTime = sunRaiseTime;
            }

            public String getSunDownTime() {
                return sunDownTime;
            }

            public void setSunDownTime(String sunDownTime) {
                this.sunDownTime = sunDownTime;
            }

            public String getTempMax() {
                return tempMax;
            }

            public void setTempMax(String tempMax) {
                this.tempMax = tempMax;
            }

            public String getTempMin() {
                return tempMin;
            }

            public void setTempMin(String tempMin) {
                this.tempMin = tempMin;
            }

            public String getUvIndex() {
                return uvIndex;
            }

            public void setUvIndex(String uvIndex) {
                this.uvIndex = uvIndex;
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
