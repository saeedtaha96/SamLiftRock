package com.samlifttruck.activity.Models;

public class ProductModel {

    private String sProductCode;
    private String sTechNo;
    private String sProductName;
    private String sMainUnitID;
    private String sProductTypeID;
    private String sShelf;
    private String OrderPoint;
    private String OrderCount;
    private String Description;
    private String SerialNo;
    private String s91;
    private String s92;
    private String withTax;

    public ProductModel(String sProductCode, String sTechNo, String sProductName, String sMainUnitID, String sProductTypeID, String sShelf, String orderPoint, String orderCount, String description, String serialNo, String s91, String s92, String withTax) {
        this.sProductCode = sProductCode;
        this.sTechNo = sTechNo;
        this.sProductName = sProductName;
        this.sMainUnitID = sMainUnitID;
        this.sProductTypeID = sProductTypeID;
        this.sShelf = sShelf;
        OrderPoint = orderPoint;
        OrderCount = orderCount;
        Description = description;
        SerialNo = serialNo;
        this.s91 = s91;
        this.s92 = s92;
        this.withTax = withTax;
    }

    public ProductModel(String sProductName, String sShelf) {
        this.sProductName = sProductName;
        this.sShelf = sShelf;
    }

    public String getsProductCode() {
        return sProductCode;
    }

    public void setsProductCode(String sProductCode) {
        this.sProductCode = sProductCode;
    }

    public String getsTechNo() {
        return sTechNo;
    }

    public void setsTechNo(String sTechNo) {
        this.sTechNo = sTechNo;
    }

    public String getsProductName() {
        return sProductName;
    }

    public void setsProductName(String sProductName) {
        this.sProductName = sProductName;
    }

    public String getsMainUnitID() {
        return sMainUnitID;
    }

    public void setsMainUnitID(String sMainUnitID) {
        this.sMainUnitID = sMainUnitID;
    }

    public String getsProductTypeID() {
        return sProductTypeID;
    }

    public void setsProductTypeID(String sProductTypeID) {
        this.sProductTypeID = sProductTypeID;
    }

    public String getsShelf() {
        return sShelf;
    }

    public void setsShelf(String sShelf) {
        this.sShelf = sShelf;
    }

    public String getOrderPoint() {
        return OrderPoint;
    }

    public void setOrderPoint(String orderPoint) {
        OrderPoint = orderPoint;
    }

    public String getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(String orderCount) {
        OrderCount = orderCount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getS91() {
        return s91;
    }

    public void setS91(String s91) {
        this.s91 = s91;
    }

    public String getS92() {
        return s92;
    }

    public void setS92(String s92) {
        this.s92 = s92;
    }

    public String getWithTax() {
        return withTax;
    }

    public void setWithTax(String withTax) {
        this.withTax = withTax;
    }
}
