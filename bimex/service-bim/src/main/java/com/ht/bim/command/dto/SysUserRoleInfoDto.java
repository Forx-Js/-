package com.ht.bim.command.dto;

import ht.framework.util.Validator;

/**
 * Created by dlj on 2019-1-15.
 */
public class SysUserRoleInfoDto {
    private String roleID;
    private String keyWord;

    public void validate() {
        Validator.notBlank(roleID, "角色编号不能为空");
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

}
