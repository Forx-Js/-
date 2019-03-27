package ht.service.auth.po;

import java.io.Serializable;

/**
 * Created by zeng.ling on 2017/11/23.
 */
public class UserSecurityToken implements Serializable {
    private String userId;
    private String password;
    private String status;
    private String salt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalt() { return salt; }

    public void setSalt(String salt) { this.salt = salt; }
}
