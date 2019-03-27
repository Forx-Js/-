package com.ht.bim.command.dto;

import ht.framework.util.Validator;

/**
 * Created by dlj on 2019-1-15.
 */
public class SysModuleDto {
    private String pID;
    private String roleID;
    private String moduleID;
    private String limit;

    public void validateCreate() {
        Validator.notBlank(roleID, "角色编号不能为空");
        Validator.notBlank(moduleID, "模块编号不能为空");
        Validator.notBlank(limit, "读写标识不能为空");
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

}
