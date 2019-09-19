package com.samlifttruck.activity.Models;

public class MidtermControlModel {
    private String productName;
    private int productCode;
    private String shelfNum;
    private String unitID;
    private String Inventory;
    private String techNo;
    private String currCount;

    public MidtermControlModel() {
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShelfNum() {
        return shelfNum;
    }

    public void setShelfNum(String shelfNum) {
        this.shelfNum = shelfNum;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public String getInventory() {
        return Inventory;
    }

    public void setInventory(String inventory) {
        Inventory = inventory;
    }

    public String getTechNo() {
        return techNo;
    }

    public void setTechNo(String techNo) {
        this.techNo = techNo;
    }

    public String getCurrCount() {
        return currCount;
    }

    public void setCurrCount(String currCount) {
        this.currCount = currCount;
    }
}
