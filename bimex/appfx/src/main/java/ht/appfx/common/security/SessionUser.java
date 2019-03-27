package ht.appfx.common.security;

/**
 * Created by zeng.ling on 2017/11/16.
 */

import ht.service.auth.po.Department;
import ht.service.auth.view.MenuItemView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户
 */
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private String token;
    private String terminalType;

    /**
     * 账户号。内部标识
     */
    private String userId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 登录用户名
     */
    private String loginUserNo;

    private String clientIp;

    private Date loginTime;

    private List<String> departments;
    private List<Department> departmentsAll;

    /**
     * 用户的权限
     */
    private List<String> funcList;
    /**
     * 菜单
     */
    private List<MenuItemView> menuList;

    @Override
    public String toString() {
        return "SessionUser [userId=" + userId + ", username=" + userName + ", loginTime=" + loginTime + "]";
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
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

    public String getLoginUserNo() {
        return loginUserNo;
    }

    public void setLoginUserNo(String loginUserNo) {
        this.loginUserNo = loginUserNo;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public List<String> getFuncList() {
        return funcList;
    }

    public void setFuncList(List<String> funcList) {
        this.funcList = funcList;
    }

    public List<MenuItemView> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuItemView> menuList) {
        this.menuList = menuList;
    }

    public List<Department> getDepartmentsAll() {
        return departmentsAll;
    }

    public void setDepartmentsAll(List<Department> departmentsAll) {
        this.departmentsAll = departmentsAll;
    }
}