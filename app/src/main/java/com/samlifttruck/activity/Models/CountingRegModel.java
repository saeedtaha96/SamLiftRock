package com.samlifttruck.activity.Models;

import java.util.PropertyPermission;

public class CountingRegModel {
    private int productCode = 0;
    private String techNo = " ";
    private String productName = " ";
    private int onHand = 0;
    private int count1 = 0;
    private int count2 = 0;
    private int count3 = 0;
    private int countResult_1_2 = 0;
    private int finalResult = 0;
    private String shelfNum = " ";

    public CountingRegModel() {
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getTechNo() {
        return techNo;
    }

    public void setTechNo(String techNo) {
        this.techNo = techNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOnHand() {
        return onHand;
    }

    public void setOnHand(int onHand) {
        this.onHand = onHand;
    }

    public int getCount1() {
        return count1;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    public int getCount2() {
        return count2;
    }

    public void setCount2(int count2) {
        this.count2 = count2;
    }

    public int getCount3() {
        return count3;
    }

    public void setCount3(int count3) {
        this.count3 = count3;
    }

    public int getCountResult_1_2() {
        return countResult_1_2;
    }

    public void setCountResult_1_2(int countResult_1_2) {
        this.countResult_1_2 = countResult_1_2;
    }

    public int getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(int finalResult) {
        this.finalResult = finalResult;
    }

    public String getShelfNum() {
        return shelfNum;
    }

    public void setShelfNum(String shelfNum) {
        this.shelfNum = shelfNum;
    }
}
