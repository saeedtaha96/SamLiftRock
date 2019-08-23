package com.samlifttruck.activity.Models;

public class ReceiptListModel {

    private String draftNum;
    private String receiptNum;
    private String custName;
    private String date;

    public ReceiptListModel(String draftNum, String receiptNum, String custName, String date) {
        this.draftNum = draftNum;
        this.custName = custName;
        this.receiptNum = receiptNum;
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

    public String getReceiptNum() {
        return receiptNum;
    }

    public void setRceiptNum(String permNum) {
        this.receiptNum = permNum;
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
