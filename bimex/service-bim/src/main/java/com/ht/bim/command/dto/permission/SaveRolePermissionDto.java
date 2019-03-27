package com.ht.bim.command.dto.permission;

import ht.framework.util.Validator;

import java.util.List;

/**
 * Created by zhong on 2019-1-31.
 */
public class SaveRolePermissionDto {
    private String roleID;
    private List<ModuleAccessDto> modules;

    public void validate() {
        Validator.notBlank(roleID, "角色编号不能为空");
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public List<ModuleAccessDto> getModules() {
        return modules;
    }

    public void setModules(List<ModuleAccessDto> modules) {
        this.modules = modules;
    }
}
