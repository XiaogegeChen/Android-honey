package com.github.xiaogegechen.weather.model.json;

import com.github.xiaogegechen.common.network.MyServerCheckable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SatelliteTypeJSON implements MyServerCheckable {
    private Result result;
    @SerializedName("error_code")
    private String errorCode;
    @SerializedName("error_message")
    private String errorMessage;

    @Override
    public String errorCode() {
        return errorCode;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }

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

    public static class Result {
        private String count;
        @SerializedName("t_list")
        private List<Type> typeList;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<Type> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<Type> typeList) {
            this.typeList = typeList;
        }

        public static class Type {
            @SerializedName("name")
            private String typeName;
            @SerializedName("num")
            private String typeNum;

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getTypeNum() {
                return typeNum;
            }

            public void setTypeNum(String typeNum) {
                this.typeNum = typeNum;
            }
        }
    }
}
