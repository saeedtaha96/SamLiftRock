package com.samlifttruck.activity.Models;

public class UnitInfoModel {
    private int unitID;
    private String unitName;

    public UnitInfoModel(int unitID, String unitName) {
        this.unitID = unitID;
        this.unitName = unitName;
    }

    public UnitInfoModel() {
    }

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
