package com.ht.bim.iam.model;

/**
 * Created by zeng.ling on 2019-3-26.
 */
public class UserInfo {
    /**
     * 非必须, 用户id
     */
    private String userId;
    /**
     * 非必须, 用户名：oa账号或外部账号
     */
    private String userName;
    /**
     * 非必须, 员工姓名
     */
    private String realName;
    /**
     * 非必须, 组织id
     */
    private String orgId;
    /**
     * 非必须, 工号
     */
    private String employId;
    /**
     * 非必须, 类型
     */
    private Integer type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getEmployId() {
        return employId;
    }

    public void setEmployId(String employId) {
        this.employId = employId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
