package com.ht.bim.iam.model;

/**
 * Created by zeng.ling on 2019-3-26.
 */
public class UserInfo2 {
    /**
     * 必须, 用户id，全局唯一
     */
    private String userId;
    /**
     * 必须, oa账号或供方账号
     */
    private String userName;
    /**
     * 必须, 真实姓名
     */
    private String realName;
    /**
     * 必须, 工号
     */
    private String employId;
    /**
     * 必须, 用户类型：1-内部员工，2-供方员工，3-供方开发者
     */
    private Integer type;
    /**
     * 必须, 公司
     */
    private String company;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
