package ht.appfx;

import ht.framework.exception.BusinessException;
import ht.framework.util.Validator;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * Created by zhong on 2018/7/15.
 */
public class TestLdap {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLdap.class);

    @Test
    @Ignore
    public void login() {
        String userName = "zeng.ling";
        String password = "z.505348";
        DirContext ctx = getCtx(userName, password);
        closeCtx(ctx);
    }

    public DirContext getCtx(String userName, String password) {
        String ldapUrl = "ldap://10.32.16.2:389";
        String domainName = "CCDI";
        Validator.notBlank(ldapUrl, "未设置 ldap 参数(url)");
        Validator.notBlank(domainName, "未设置 ldap 参数(domain)");
        String dn = "cn=" + userName + ",cn=Users,DC=" + domainName + "";
        System.out.println(dn);
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
//        env.put(Context.SECURITY_PRINCIPAL, dn);
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

    public static void closeCtx(DirContext ctx) {
        try {
            ctx.close();
        } catch (NamingException ex) {
            LOGGER.error("关闭 ldap 异常", ex);
        }
    }
}
