package ht.service.auth.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tianqi.zhang on 2018/4/3.
 */
public class UserPassPo implements Serializable {
    private String userId;
    private String userName;
    private String password;
    private String name;
    private String phoneNum;
    private String status;
    private Boolean ifOpenWebLogin;
    private Boolean ifOpenAppLogin;
    private Date firstWebLoginTime;
    private Date firstAppLoginTime;
    private String salt;
    private Date lastOperTime;
    private String ifForceUpdate;

    public String getIfForceUpdate() {
        return ifForceUpdate;
    }

    public void setIfForceUpdate(String ifForceUpdate) {
        this.ifForceUpdate = ifForceUpdate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Boolean getIfOpenWebLogin() {
        return ifOpenWebLogin;
    }

    public void setIfOpenWebLogin(Boolean ifOpenWebLogin) {
        this.ifOpenWebLogin = ifOpenWebLogin;
    }

    public Boolean getIfOpenAppLogin() {
        return ifOpenAppLogin;
    }

    public void setIfOpenAppLogin(Boolean ifOpenAppLogin) {
        this.ifOpenAppLogin = ifOpenAppLogin;
    }

    public Date getFirstWebLoginTime() {
        return firstWebLoginTime;
    }

    public void setFirstWebLoginTime(Date firstWebLoginTime) {
        this.firstWebLoginTime = firstWebLoginTime;
    }

    public Date getFirstAppLoginTime() {
        return firstAppLoginTime;
    }

    public void setFirstAppLoginTime(Date firstAppLoginTime) {
        this.firstAppLoginTime = firstAppLoginTime;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getLastOperTime() {
        return lastOperTime;
    }

    public void setLastOperTime(Date lastOperTime) {
        this.lastOperTime = lastOperTime;
    }
}