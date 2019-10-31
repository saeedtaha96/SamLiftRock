package com.samlifttruck.activity.Models;

public class PermListModel {
    private int businessID = 0;
    private String permNum = "-";
    private String date = "-";
    private String custName = "-";
    private String preFactorNum = "-";
    private String condition = "-";
    private String descrip = "-";

    public PermListModel() {
    }

    public PermListModel(String permNum, String date, String custName, String preFactorNum, String condition, String descrip) {
        this.permNum = permNum;
        this.date = date;
        this.custName = custName;
        this.preFactorNum = preFactorNum;
        this.condition = condition;
        this.descrip = descrip;
    }

    public int getBusinessID() {
        return businessID;
    }

    public void setBusinessID(int businessID) {
        this.businessID = businessID;
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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPreFactorNum() {
        return preFactorNum;
    }

    public void setPreFactorNum(String preFactorNum) {
        this.preFactorNum = preFactorNum;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    @Override
    public String toString() {
        return preFactorNum + " of " + custName;
    }
}
