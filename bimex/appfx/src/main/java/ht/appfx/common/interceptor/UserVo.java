package ht.appfx.common.interceptor;

/**
 * TODO 这个类的定义，以及它的位置只是临时放置。
 * Created by zhong on 2018/6/12.
 */
public class UserVo {
    private String userId;
    private String userName;
    private String name;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
