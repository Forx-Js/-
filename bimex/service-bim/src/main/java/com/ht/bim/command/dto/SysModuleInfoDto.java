package com.ht.bim.command.dto;

import ht.framework.util.Validator;

import java.util.Date;

/**
 * Created by dlj on 2019-1-17.
 */
public class SysModuleInfoDto {
    private String moduleID;
    private String moduleName;
    private String pID;
    private String moduleType;
    private String url;
    private String icon;
    private Integer nodeType;
    private String operUserID;
    private Date operdate;
    private String moduleNameEn;
    private Integer sortNum;
    private String funcName;

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public void validate() {
        Validator.notBlank(moduleNameEn, "权限控制名称不能位空");
    }

    public void validateUpdate() {
        Validator.notBlank(moduleID, "模块编号不能为空");
        Validator.notBlank(moduleName, "模块名称不能为空");
        Validator.notBlank(moduleType, "模块类型不能为空");
        validate();
    }

    public void validateCreate() {
        Validator.notBlank(moduleName, "模块名称不能为空");
        Validator.notBlank(moduleType, "模块类型不能为空");
        Validator.notBlank(sortNum.toString(),"排序号不能为空");
        validate();
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getModuleNameEn() {
        return moduleNameEn;
    }

    public void setModuleNameEn(String moduleNameEn) {
        this.moduleNameEn = moduleNameEn;
    }

    public String getOperUserID() {
        return operUserID;
    }

    public void setOperUserID(String operUserID) {
        this.operUserID = operUserID;
    }

    public Date getOperdate() {
        return operdate;
    }

    public void setOperdate(Date operdate) {
        this.operdate = operdate;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
