package com.samlifttruck.activity.Models;

public class DraftListModel {
    private int businessID = 0;
    private String draftNum = "-";
    private String permNum = "-";
    private String receiver = "-";
    private String date = "-";
    private String draftType = "-";
    private String servicePage = "-";
    private String condition = "-";
    private String description = "-";

    public DraftListModel() {
    }



    public int getBusinessID() {
        return businessID;
    }

    public void setBusinessID(int businessID) {
        this.businessID = businessID;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public void setServicePage(String servicePage) {
        this.servicePage = servicePage;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}