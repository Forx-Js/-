package com.ht.bim.query.vo;

import java.util.List;

/**
 * Created by dlj on 2019-1-15.
 */
public class SysModuleQueryVo {
    private String moduleID;
    private String moduleName;
    private String pID;
    private Integer limit;
    private Integer sortNum;
    private String moduleNameEn;
    private String url;
    private String funcName;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getModuleNameEn() {
        return moduleNameEn;
    }

    public void setModuleNameEn(String moduleNameEn) {
        this.moduleNameEn = moduleNameEn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    private List<SysModuleQueryVo> sysModuleQueryVo;

    public List<SysModuleQueryVo> getSysModuleQueryVo() {
        return sysModuleQueryVo;
    }

    public void setSysModuleQueryVo(List<SysModuleQueryVo> sysModuleQueryVo) {
        this.sysModuleQueryVo = sysModuleQueryVo;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
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

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
