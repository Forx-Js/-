package ht.appfx.common.shiro;

import ht.appfx.common.security.ClientTypes;
import ht.framework.util.TimeSource;
import ht.service.auth.po.DbSession;
import ht.service.auth.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by zhong on 2018/1/16.
 */
@Service
public class DbSessionDAO extends JwtSessionDAO {
    @Resource
    private AuthService authService;
    @Resource
    private TimeSource timeSource;

    @Override
    protected Serializable doCreate(Session session) {
        super.doCreate(session);
        String sessionString = SerializableUtils.serialize(session);
        DbSession dbSession = new DbSession();
        dbSession.setToken(session.getId().toString());
        dbSession.setSessionString(sessionString);
        dbSession.setLoginTime(timeSource.getCurrentTime());
//        SessionUser user = SessionUserContext.getUser();
//        if (user != null) {
//            dbSession.setUserId(user.getUserId());
//        }
//        Subject subject = SecurityUtils.getSubject();
//        String loginName = subject.getPrincipal().toString();
//        dbSession.setUserId(loginName);
        authService.createDbSession(dbSession);
        return session.getId();
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        DbSession dbSession = authService.findSession(sessionId.toString());
        if (dbSession != null) {
            String sessionString = dbSession.getSessionString();
            Session session = null;
            if (StringUtils.isBlank(sessionString)) {
                return session;
            }
            session = SerializableUtils.deserialize(sessionString);
            resolveTokenSession(session);
            return session;
        } else {
//            SimpleSession simpleSession = new SimpleSession();
//            simpleSession.setId(sessionId);
//            return simpleSession;
            return null;
        }
    }

    private void resolveTokenSession(Session session) {
        if (session instanceof SimpleSession) {
            SimpleSession simpleSession = (SimpleSession) session;
            String clientType = (String) simpleSession.getAttribute(ClientTypes.CLIENT_TYPE_KEY);
            if (StringUtils.equals(clientType, ClientTypes.APP)) {
                simpleSession.touch();
            }
        }
    }

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            //如果会话过期/停止没必要再更新了
            return;
        }
        String token = session.getId().toString();
        DbSession dbSession = authService.findSession(token);
        if (dbSession != null) {
            String sessionString = SerializableUtils.serialize(session);
            dbSession.setSessionString(sessionString);
            authService.updateSession(dbSession);
        }
    }

    @Override
    protected void doDelete(Session session) {
        String token = session.getId().toString();
        authService.deleteSession(token);
    }

    /**
     * TODO finish it
     *
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }
}
