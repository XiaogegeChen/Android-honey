package com.github.xiaogegechen.module_d.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 快递查询结果的JSON
 */
public class ExpressJSON {

    /**
     *  status 0:正常查询 201:快递单号错误 203:快递公司不存在 204:快递公司识别失败 205:没有信息 207:该单号被限制，错误单号
     */
    @SerializedName("status")
    private String statusCode;

    /**
     * 简要信息，如："ok"， "没有信息"
     */
    @SerializedName("msg")
    private String message;

    /**
     * 查询详细结果
     */
    private Result result;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {

        /**
         * 快递单号
         */
        private String number;

        /**
         * 快递公司简写
         */
        private String type;

        /**
         * 运动轨迹
         */
        @SerializedName("list")
        private List<MotionTrack> motionTrackList;

        /**
         * 派发状态  0：快递收件(揽件)1.在途中 2.正在派件 3.已签收 4.派送失败 5.疑难件 6.退件签收
         */
        @SerializedName("deliverystatus")
        private String deliveryStatus;

        /**
         * 是否签收，1 说明是签收的
         */
        @SerializedName("issign")
        private String isSign;

        /**
         * 快递公司名称，中文名，如："中通快递"
         */
        @SerializedName("expName")
        private String expressName;

        /**
         * 快递公司网址，如"www.zto.com"
         */
        @SerializedName("expSite")
        private String expressSite;

        /**
         * 快递公司服务热线，如："95311"
         */
        @SerializedName("expPhone")
        private String expressPhone;

        /**
         * 快递员姓名，如："容晓光"
         */
        @SerializedName("courier")
        private String messengerName;

        /**
         * 快递员手机号，如："13081105270"
         */
        @SerializedName("courierPhone")
        private String messengerPhone;

        /**
         * 快递轨迹信息最新时间
         */
        private String updateTime;

        /**
         * 发货到收货消耗时长 (截止最新轨迹)
         */
        private String takeTime;

        /**
         * 快递公司LOGO
         */
        @SerializedName("logo")
        private String expressLogoUrl;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<MotionTrack> getMotionTrackList() {
            return motionTrackList;
        }

        public void setMotionTrackList(List<MotionTrack> motionTrackList) {
            this.motionTrackList = motionTrackList;
        }

        public String getDeliveryStatus() {
            return deliveryStatus;
        }

        public void setDeliveryStatus(String deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }

        public String getIsSign() {
            return isSign;
        }

        public void setIsSign(String isSign) {
            this.isSign = isSign;
        }

        public String getExpressName() {
            return expressName;
        }

        public void setExpressName(String expressName) {
            this.expressName = expressName;
        }

        public String getExpressSite() {
            return expressSite;
        }

        public void setExpressSite(String expressSite) {
            this.expressSite = expressSite;
        }

        public String getExpressPhone() {
            return expressPhone;
        }

        public void setExpressPhone(String expressPhone) {
            this.expressPhone = expressPhone;
        }

        public String getMessengerName() {
            return messengerName;
        }

        public void setMessengerName(String messengerName) {
            this.messengerName = messengerName;
        }

        public String getMessengerPhone() {
            return messengerPhone;
        }

        public void setMessengerPhone(String messengerPhone) {
            this.messengerPhone = messengerPhone;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getTakeTime() {
            return takeTime;
        }

        public void setTakeTime(String takeTime) {
            this.takeTime = takeTime;
        }

        public String getExpressLogoUrl() {
            return expressLogoUrl;
        }

        public void setExpressLogoUrl(String expressLogoUrl) {
            this.expressLogoUrl = expressLogoUrl;
        }
    }

    public static class MotionTrack {
        /**
         * 时间点，如"2018-03-09 11:59:26"
         */
        private String time;

        /**
         * 状态描述， 如"【石家庄市】快件已在【长安三部】 签收,签收人: 本人,感谢使用中通快递,期待再次为您服务!"
         */
        private String status;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
