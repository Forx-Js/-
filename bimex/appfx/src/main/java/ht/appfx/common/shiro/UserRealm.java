package ht.appfx.common.shiro;

import ht.framework.util.Validator;
import ht.service.auth.po.User;
import ht.service.auth.po.UserSecurityToken;
import ht.service.auth.service.AuthService;
import ht.service.common.service.ParameterProvider;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

/**
 * Created by zhong on 2017/12/19.
 */
public class UserRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);

    @Resource
    private AuthService authService;
    @Resource
    private ParameterProvider parameterProvider;

    /**
     * 放入 authentication 中的 principal 是一个 User 对象
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        if (StringUtils.isEmpty(userName)) {
            return null;
        }
        User user = getValidUser(token);
        if (user != null) {
            if (parameterProvider.supportDomainLogin() && BooleanUtils.isTrue(user.getDomainAccount())) {
                return createDomainLoginAuthInfo(userName);
            } else {
                return createLocalLoginAuthInfo(user);
            }
        } else {
            return createDomainLoginAuthInfo(userName);
        }
    }

    private AuthenticationInfo createLocalLoginAuthInfo(User user) {
        UserSecurityToken securityToken = authService.findSecurityTokenByName(user.getUserName());
        ByteSource salt = ByteSource.Util.bytes(securityToken.getSalt());
        SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo(user, securityToken.getPassword(), salt,
                getName());
        return auth;
    }

    private AuthenticationInfo createDomainLoginAuthInfo(String userName) {
        User user;
        user = new User();
        user.setUserName(userName);
        user.setDomainAccount(true);
        return new SimpleAuthenticationInfo(user, null, null, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        String userId = user.getUserId();
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        List<String> roles = authService.findUserRoles(userId);
        List<String> permissions = authService.findUserPermissions(userId);
        authInfo.setRoles(new HashSet<>(roles));
        authInfo.setStringPermissions(new HashSet<>(permissions));
        return authInfo;
    }

    private User getValidUser(AuthenticationToken token) {
        String userName = (String) token.getPrincipal();
        LOGGER.debug("登录用户名 {}", userName);
        User user = authService.findUserByLoginName(userName);
        // NOTE 现在仅仅是用户名作为登录名，如果需要邮箱、手机号，请在这里判断并增加逻辑
        if (!parameterProvider.supportDomainLogin()) {
            validate(user);
        }
        return user;
    }

    private void validate(User user) {
        Validator.notNull(user, "用户不存在");
    }
}
