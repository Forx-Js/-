package com.ht.bim.query.vo;

public class SysDicInfoQueryVo {
    private String dicID;
    private String dicName;
    private String editable;
    private String remark;
    private String sortNum;

    public String getDicID() {
        return dicID;
    }

    public void setDicID(String dicID) {
        this.dicID = dicID;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }
}
