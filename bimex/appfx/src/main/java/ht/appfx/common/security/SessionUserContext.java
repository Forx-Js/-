package ht.appfx.common.security;

import ht.framework.session.SecurityConstant;
import ht.framework.session.SessionWrapper;

/**
 * Created by zeng.ling on 2017/11/16.
 */
public class SessionUserContext {

    public static final String LOGIN_NAME = "LOGIN_NAME";
    public static final String CLIENT_TYPE = "CLIENT_TYPE";

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static SessionUser getUser() {
        Object obj = SessionWrapper.get(SecurityConstant.OPERATOR);
        if (obj instanceof SessionUser) {
            return (SessionUser) obj;
        } else {
            return null;
        }
    }
}
