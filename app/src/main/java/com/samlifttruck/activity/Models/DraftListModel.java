package com.samlifttruck.activity.Models;

public class DraftListModel {

    private String draftNum;
    private String permNum;
    private String custName;
    private String date;
    private String draftType;
    private String servicePage;
    private String condition;
    private String description;

    public DraftListModel(String draftNum, String permNum, String custName, String date, String draftType, String servicePage, String condition, String description) {
        this.draftNum = draftNum;
        this.permNum = permNum;
        this.custName = custName;
        this.date = date;
        this.draftType = draftType;
        this.servicePage = servicePage;
        this.condition = condition;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getServicePage() {
        return servicePage;
    }

    public void setServicePage(String userName) {
        this.servicePage = userName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}