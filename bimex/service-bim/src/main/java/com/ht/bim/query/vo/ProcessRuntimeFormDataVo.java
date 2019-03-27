package com.ht.bim.query.vo;

import java.util.Date;

/**
 * Created by zhong on 2019-2-7.
 */
public class ProcessRuntimeFormDataVo {
    private String runtimeFormDataID;
    private String formConfigID;
    private String runTimeID;
    private String processID;
    private String nodeID;
    private Date createTime;
    private String createUserID;
    private Date updateTime;
    private String updateUserID;
    private String dataJson;

    public String getRuntimeFormDataID() {
        return runtimeFormDataID;
    }

    public void setRuntimeFormDataID(String runtimeFormDataID) {
        this.runtimeFormDataID = runtimeFormDataID;
    }

    public String getFormConfigID() {
        return formConfigID;
    }

    public void setFormConfigID(String formConfigID) {
        this.formConfigID = formConfigID;
    }

    public String getRunTimeID() {
        return runTimeID;
    }

    public void setRunTimeID(String runTimeID) {
        this.runTimeID = runTimeID;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserID() {
        return createUserID;
    }

    public void setCreateUserID(String createUserID) {
        this.createUserID = createUserID;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserID() {
        return updateUserID;
    }

    public void setUpdateUserID(String updateUserID) {
        this.updateUserID = updateUserID;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
