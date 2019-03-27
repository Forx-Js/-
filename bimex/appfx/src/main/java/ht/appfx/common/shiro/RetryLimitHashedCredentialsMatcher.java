package ht.appfx.common.shiro;

import ht.appfx.common.security.LdapLoginChecker;
import ht.appfx.common.security.SessionUserContext;
import ht.framework.exception.BusinessException;
import ht.service.auth.po.User;
import ht.service.auth.service.AuthService;
import ht.service.common.service.ParameterProvider;
import org.apache.commons.lang.BooleanUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhong on 2017/12/19.
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    private Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);
    private Cache<String, AtomicInteger> passwordRetryCache;
    private static final int MAX_RETRY_TIMES = 5;

    @Resource
    private LdapLoginChecker ldapLoginChecker;
    @Resource
    private AuthService authService;
    @Resource
    private ParameterProvider parameterProvider;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        if (cacheManager != null) {
            passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        }
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo authentication) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String loginName = usernamePasswordToken.getUsername();
        checkFailedRetryTimes(loginName);
        boolean matched = doLogin(token, authentication);
        if (matched) {
            ThreadContext.put(SessionUserContext.LOGIN_NAME, loginName);
            removeFailedRetryTimes(loginName);
        } else {
            increaseFailedTimes(loginName);
        }
        return matched;
    }

    private boolean doLogin(AuthenticationToken token, AuthenticationInfo authentication) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String loginName = usernamePasswordToken.getUsername();
        User user = (User) authentication.getPrincipals().getPrimaryPrincipal();
        if (parameterProvider.supportDomainLogin() && BooleanUtils.isTrue(user.getDomainAccount())) {
            String password = new String((char[]) usernamePasswordToken.getCredentials());
            return domainLogin(loginName, password);
        } else {
            return super.doCredentialsMatch(token, authentication);
        }
    }

    private boolean domainLogin(String loginName, String password) {
        try {
            ldapLoginChecker.login(loginName, password);
            User user = authService.findUserByLoginName(loginName);
            if (user == null) {
                authService.createDomainUser(loginName, loginName);
            }
            return true;
        } catch (BusinessException e) {
            return false;
        }
    }

    private void increaseFailedTimes(String userName) {
        try {
            AtomicInteger retryTimes = passwordRetryCache.get(userName);
            if (retryTimes == null) {
                retryTimes = new AtomicInteger(1);
                passwordRetryCache.put(userName, retryTimes);
            } else {
                retryTimes.incrementAndGet();
            }
        } catch (Exception ex) {
            // DO-NOTHING
        }
    }

    private void removeFailedRetryTimes(String userName) {
        passwordRetryCache.remove(userName);
    }

    private void checkFailedRetryTimes(String userName) {
        AtomicInteger retryTimes = passwordRetryCache.get(userName);
        if (retryTimes != null) {
            if (retryTimes.get() > MAX_RETRY_TIMES) {
                throw new ExcessiveAttemptsException();
            }
        }
    }

}
