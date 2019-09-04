package com.samlifttruck.activity.Models;

public class ReceiptListModel {

    private String receiptNum = "";
    private String date = "";
    private String productSource = "";
    private String receiptType = "";
    private String condition = "";
    private String descrip1 = "";
    private String descrip2 = "";
    private String descrip3 = "";

    public ReceiptListModel() {
    }

    public ReceiptListModel(String receiptNum, String date, String productSource, String receiptType, String condition, String descrip1, String descrip2, String descrip3) {
        this.receiptNum = receiptNum;
        this.date = date;
        this.productSource = productSource;
        this.receiptType = receiptType;
        this.condition = condition;
        this.descrip1 = descrip1;
        this.descrip2 = descrip2;
        this.descrip3 = descrip3;
    }

    public String getReceiptNum() {
        return receiptNum;
    }

    public void setReceiptNum(String receiptNum) {
        this.receiptNum = receiptNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductSource() {
        return productSource;
    }

    public void setProductSource(String productSource) {
        this.productSource = productSource;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescrip1() {
        return descrip1;
    }

    public void setDescrip1(String descrip1) {
        this.descrip1 = descrip1;
    }

    public String getDescrip2() {
        return descrip2;
    }

    public void setDescrip2(String descrip2) {
        this.descrip2 = descrip2;
    }

    public String getDescrip3() {
        return descrip3;
    }

    public void setDescrip3(String descrip3) {
        this.descrip3 = descrip3;
    }
}
