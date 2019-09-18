package com.samlifttruck.activity.Models;

public class ProductTypeInfoModel {

    private int productTypeID;
    private String productTypeName;

    public ProductTypeInfoModel() {
    }

    public int getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(int productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }
}
