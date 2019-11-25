package com.github.xiaogegechen.module_d.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BingPictureModuleJSON {
    /**
     * result : {"module_count":6,"module_list":[{"module_type":200,"module_title":" 今日热图"},{"module_type":201,"module_title":" 明星"},{"module_type":202,"module_title":" 美女"},{"module_type":203,"module_title":" 摄影"},{"module_type":204,"module_title":" 壁纸"},{"module_type":205,"module_title":" 动漫"}]}
     * error_code : 0
     * error_message : success
     */

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

    public static class Result {
        /**
         * module_count : 6
         * module_list : [{"module_type":200,"module_title":" 今日热图"},{"module_type":201,"module_title":" 明星"},{"module_type":202,"module_title":" 美女"},{"module_type":203,"module_title":" 摄影"},{"module_type":204,"module_title":" 壁纸"},{"module_type":205,"module_title":" 动漫"}]
         */

        @SerializedName("module_count")
        private String moduleCount;
        @SerializedName("module_list")
        private List<ModuleList> moduleList;

        public String getModuleCount() {
            return moduleCount;
        }

        public void setModuleCount(String moduleCount) {
            this.moduleCount = moduleCount;
        }

        public List<ModuleList> getModuleList() {
            return moduleList;
        }

        public void setModuleList(List<ModuleList> moduleList) {
            this.moduleList = moduleList;
        }

        public static class ModuleList {
            /**
             * module_type : 200
             * module_title :  今日热图
             */

            @SerializedName("module_type")
            private String moduleType;
            @SerializedName("module_title")
            private String moduleTitle;

            public String getModuleType() {
                return moduleType;
            }

            public void setModuleType(String moduleType) {
                this.moduleType = moduleType;
            }

            public String getModuleTitle() {
                return moduleTitle;
            }

            public void setModuleTitle(String moduleTitle) {
                this.moduleTitle = moduleTitle;
            }
        }
    }
}
