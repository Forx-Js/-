package com.ht.bim;

import ht.appfx.common.shiro.AjaxFormAuthenticationFilter;
import ht.appfx.common.shiro.DbSessionDAO;
import ht.appfx.common.shiro.RetryLimitHashedCredentialsMatcher;
import ht.appfx.common.shiro.TokenSessionManager;
import ht.appfx.common.shiro.UserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Shiro 配置
 */
@Configuration
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Value("${server.session.timeout}")
    private String serverSessionTimeout;

    @Bean
    public UserRealm userRealm(CredentialsMatcher credentialsMatcher) {
        UserRealm realm = new UserRealm();
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    @Bean
    public RetryLimitHashedCredentialsMatcher credentialsMatcher(EhCacheManager cacheManager) {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
        credentialsMatcher.setHashIterations(1);
        credentialsMatcher.setHashAlgorithmName("SHA-256");
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean
    public EhCacheManager cacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    /**
     * 注册DelegatingFilterProxy（Shiro）
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.addInitParameter("serverSessionTimeout", serverSessionTimeout);
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(cacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public TokenSessionManager sessionManager() {
        TokenSessionManager sessionManager = new TokenSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }

    @Bean
    public Cookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setHttpOnly(true);
        cookie.setName("token");
        return cookie;
    }

    @Bean(value = "sessionDAO")
    public SessionDAO sessionDAO() {
        return new DbSessionDAO();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(UserRealm userRealm) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager(userRealm));
        return new AuthorizationAttributeSourceAdvisor();
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置)
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(UserRealm userRealm) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager(userRealm));
        shiroFilterFactoryBean.setLoginUrl("/auth/login");
        shiroFilterFactoryBean.setSuccessUrl("/home");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        setFilterChain(shiroFilterFactoryBean);
        setFilters(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }

    private void setFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/common/enc", "anon");
        filterChainDefinitionMap.put("/common/enc*", "anon");
        filterChainDefinitionMap.put("/resources/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/jQuery.1.10.2/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/images/login/**", "anon");
        filterChainDefinitionMap.put("/images/specIcon/username_icon.png", "anon");
        filterChainDefinitionMap.put("/images/specIcon/password_icon.png", "anon");
        filterChainDefinitionMap.put("/video/**", "anon");
        filterChainDefinitionMap.put("/auth/login*", "anon");
        filterChainDefinitionMap.put("/auth/logout", "anon");
        filterChainDefinitionMap.put("/update.html", "anon");
        /*filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");*/
        //放行swagger
        filterChainDefinitionMap.put("/doc.html","anon");
        filterChainDefinitionMap.put("/swagger/**","anon");
        filterChainDefinitionMap.put("/webjars*/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**","anon");
        filterChainDefinitionMap.put("/v2/**","anon");
        filterChainDefinitionMap.put("/druid/**","anon");
        filterChainDefinitionMap.put("/swagger-ui.html","anon");



        filterChainDefinitionMap.put("/400", "anon");
        filterChainDefinitionMap.put("/404", "anon");
        filterChainDefinitionMap.put("/error", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    private void setFilters(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, Filter> map = shiroFilterFactoryBean.getFilters();
        if (map == null) {
            map = new HashMap<>(8);
        }
        map.put("authc", new AjaxFormAuthenticationFilter());
    }

}
