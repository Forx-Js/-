package ht.appfx.common.shiro;

import ht.appfx.common.security.JwtToken;
import ht.appfx.common.security.SessionUserContext;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by zhong on 2018/7/15.
 */
public class JwtSessionDAO extends EnterpriseCacheSessionDAO {

    @Resource
    private JwtToken jwtToken;


    @Override
    protected Serializable generateSessionId(Session session) {
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        if (StringUtils.isBlank(principal)) {
            principal = (String) ThreadContext.get(SessionUserContext.LOGIN_NAME);
        }
        if (StringUtils.isNotBlank(principal)) {
            return jwtToken.createToken(principal);
        } else {
            return null;
        }
    }
}
