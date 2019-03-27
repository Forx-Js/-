package com.ht.bim.command.po.permission;

import java.util.Date;

/**
 * Created by dlj on 2019-1-17.
 */
public class SysModuleInfoWebPo {
    private String moduleID;
    private String permissionName;
    private String url;
    private String webModuleType;
    private String icon;
    private Integer sortNum;
    private String remark;
    private String operUserID;
    private Date operDate;

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebModuleType() {
        return webModuleType;
    }

    public void setWebModuleType(String webModuleType) {
        this.webModuleType = webModuleType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getOperUserID() {
        return operUserID;
    }

    public void setOperUserID(String operUserID) {
        this.operUserID = operUserID;
    }

    public Date getOperDate() {
        return operDate;
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }
}
