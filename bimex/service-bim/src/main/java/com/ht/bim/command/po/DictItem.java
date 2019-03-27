package com.ht.bim.command.po;

import ht.framework.util.Validator;

/**
 * Created by zeng.ling on 2017/11/26.
 */
public class DictItem {
    private String id;
    private String cnName;
    private String enName;
    private String pID;
    private String dicCode;
    private Integer sortNum;
    private String remark;

    public void validate() {
        Validator.notBlank(dicCode, "客户名称不能为空");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
