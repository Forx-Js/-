package ht.appfx.common.security;

import ht.framework.exception.BusinessException;
import ht.framework.util.Validator;
import ht.service.common.service.ParameterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * Created by zhong on 2018/7/15.
 */
@Component
public class LdapLoginChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(LdapLoginChecker.class);

    @Resource
    private ParameterProvider parameterProvider;

    public void login(String userName, String password) {
        DirContext ctx = getCtx(userName, password);
        closeCtx(ctx);
    }

    private DirContext getCtx(String userName, String password) {
        String ldapUrl = parameterProvider.ldapUrl();
        String domainName = parameterProvider.ldapDomain();
        Validator.notBlank(ldapUrl, "未设置 ldap 参数(url)");
        Validator.notBlank(domainName, "未设置 ldap 参数(domain)");
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, userName + "@" + domainName);
        env.put(Context.SECURITY_CREDENTIALS, password);
        try {
            return new InitialDirContext(env);
        } catch (javax.naming.AuthenticationException e) {
            LOGGER.error("认证失败", e);
            throw new BusinessException("认证失败", e);
        } catch (Exception e) {
            LOGGER.error("认证出错：", e);
            throw new BusinessException("认证失败", e);
        }
    }

    private static void closeCtx(DirContext ctx) {
        try {
            ctx.close();
        } catch (NamingException ex) {
            LOGGER.error("关闭 ldap 异常", ex);
        }
    }
}
