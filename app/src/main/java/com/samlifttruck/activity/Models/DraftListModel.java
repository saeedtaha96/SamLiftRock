package com.samlifttruck.activity.Models;

public class DraftListModel {

    private String draftNum;
    private String permNum;
    private String custName;
    private String date;
    private String draftType;
    private String userName;
    private String condition;

    public DraftListModel(String draftNum, String permNum, String custName, String date, String draftType, String userName, String condition) {
        this.draftNum = draftNum;
        this.permNum = permNum;
        this.custName = custName;
        this.date = date;
        this.draftType = draftType;
        this.userName = userName;
        this.condition = condition;
    }

    public String getDraftNum() {
        return draftNum;
    }

    public void setDraftNum(String draftNum) {
        this.draftNum = draftNum;
    }

    public String getPermNum() {
        return permNum;
    }

    public void setPermNum(String permNum) {
        this.permNum = permNum;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDraftType() {
        return draftType;
    }

    public void setDraftType(String draftType) {
        this.draftType = draftType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}