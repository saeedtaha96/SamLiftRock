package com.samlifttruck.activity.Models;

public class DraftListModel {

    private String draftNum;
    private String permNum;
    private String custName;
    private String date;

    public DraftListModel(String draftNum, String permNum, String custName, String date) {
        this.draftNum = draftNum;
        this.custName = custName;
        this.permNum = permNum;
        this.date = date;
    }

    public String getDraftNum() {
        return draftNum;
    }

    public void setDraftNum(String draftNum) {
        this.draftNum = draftNum;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPermNum() {
        return permNum;
    }

    public void setPermNum(String permNum) {
        this.permNum = permNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return draftNum + " of " + custName;
    }
}
