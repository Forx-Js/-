package com.ht.bim.command.po;

import java.util.Date;

/**
 * Created by zhong on 2019-3-17.
 */
public class SysFormJsonPo {
    private String formJsonID;
    private String formID;
    private String formJson;
    private Date createTime;
    private String createUserID;

    public String getFormJsonID() {
        return formJsonID;
    }

    public void setFormJsonID(String formJsonID) {
        this.formJsonID = formJsonID;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getFormJson() {
        return formJson;
    }

    public void setFormJson(String formJson) {
        this.formJson = formJson;
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
}
