package com.xiaogege.honey.ui;

import java.util.List;

public class Express {
    private String number;
    private String type;
    private String deliverystatus;
    private String issign;
    private String expName;
    private String expSite;
    private String expPhone;
    private String courierl;
    private String courierPhone;
    private List<ExpressList> expressList;

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

    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public String getIssign() {
        return issign;
    }

    public void setIssign(String issign) {
        this.issign = issign;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpSite() {
        return expSite;
    }

    public void setExpSite(String expSite) {
        this.expSite = expSite;
    }

    public String getExpPhone() {
        return expPhone;
    }

    public void setExpPhone(String expPhone) {
        this.expPhone = expPhone;
    }

    public String getCourierl() {
        return courierl;
    }

    public void setCourierl(String courierl) {
        this.courierl = courierl;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone;
    }

    public List<ExpressList> getExpressList() {
        return expressList;
    }

    public void setExpressList(List<ExpressList> expressList) {
        this.expressList = expressList;
    }
}
