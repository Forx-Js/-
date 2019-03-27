package com.ht.bim.command.dto.permission;

/**
 * Created by zhong on 2019-1-31.
 */
public class ModuleAccessDto {
    private String moduleID;
    private int limit;

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
