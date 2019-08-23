package com.samlifttruck.activity.Models;

public class PermListModel {

    private String preFactorNum;
    private String permNum;
    private String custName;
    private String date;

    public PermListModel(String draftNum, String permNum, String custName, String date) {
        this.preFactorNum = draftNum;
        this.custName = custName;
        this.permNum = permNum;
        this.date = date;
    }

    public String getPreFactorNum() {
        return preFactorNum;
    }

    public void setPreFactorNum(String draftNum) {
        this.preFactorNum = draftNum;
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
        return preFactorNum + " of " + custName;
    }
}
