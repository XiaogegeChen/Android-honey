package com.github.xiaogegechen.module_d.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * {"resultcode":"200","reason":"success","result":[{"id":"242","catalog":"中国文学"},{"id":"252","catalog":"人物传记"},{"id":"244","catalog":"儿童文学"},{"id":"248","catalog":"历史"},{"id":"257","catalog":"哲学"},{"id":"243","catalog":"外国文学"},{"id":"247","catalog":"小说"},{"id":"251","catalog":"心灵鸡汤"},{"id":"253","catalog":"心理学"},{"id":"250","catalog":"成功励志"},{"id":"249","catalog":"教育"},{"id":"245","catalog":"散文"},{"id":"256","catalog":"理财"},{"id":"254","catalog":"管理"},{"id":"246","catalog":"经典名著"},{"id":"255","catalog":"经济"},{"id":"258","catalog":"计算机"}],"error_code":0}
 */
public class CatalogJSON {

    @SerializedName("resultcode")
    private String resultCode;

    private String reason;

    private List<CatalogInfo> result;

    private int errorCode;

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

    public List<CatalogInfo> getResult() {
        return result;
    }

    public void setResult(List<CatalogInfo> result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
