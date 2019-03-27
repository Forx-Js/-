package ht.appfx.controller.auth;

import ht.appfx.common.interceptor.AuthIgnore;
import ht.appfx.common.interceptor.LoginUser;
import ht.appfx.common.interceptor.UserVo;
import ht.appfx.common.security.ClientTypes;
import ht.appfx.dto.LoginResult;
import ht.appfx.dto.LoginVo;
import ht.framework.result.ApiResult;
import ht.service.auth.po.User;
import ht.service.auth.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhong on 2018/6/22.
 */
@Api("与登录相关的接口")
@RestController
@RequestMapping("auth")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private AuthService authService;

    @ApiOperation("登录")
    @PostMapping("/login")
    @AuthIgnore
    public ApiResult<LoginResult> login(String username,String password) {
        //没用
        LoginVo loginVo = new LoginVo();
        loginVo.setLoginName(username);
        loginVo.setPassword(password);
        try {
            loginVo.validate();
            if (!checkLogin(loginVo)) {
                return ApiResult.fail("登录失败");
            }
            User user = authService.findUserByLoginName(loginVo.getLoginName());
            LoginResult result = new LoginResult();
            result.setToken(getSessionId());
            result.setLoginName(user.getName());
            //result.setPermissions(authService.findUserPermissions(user.getUserId()));
            return ApiResult.success(result);
        } catch (Exception ex) {
            LOGGER.debug("登录失败", ex);
            return ApiResult.fail("登录失败");
        }
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ApiResult logout(@LoginUser UserVo vo) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ApiResult.success();
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/user/info")
    public ApiResult<LoginResult> getUserInfo(@LoginUser UserVo vo) {
        LoginResult result = new LoginResult();
        User user = authService.findUser(vo.getUserId());
        result.setToken(getSessionId());
        result.setLoginName(user.getName());
        result.setPermissions(authService.findUserPermissions(user.getUserId()));
        return ApiResult.success(result);
    }

    private boolean checkLogin(LoginVo loginVo) {
        shiroLogin(loginVo);
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute(ClientTypes.CLIENT_TYPE_KEY, loginVo.getClientType());
        return true;
    }

    private void shiroLogin(LoginVo loginVo) {
        Subject subject = SecurityUtils.getSubject();
        logoutIfNecessary(subject);
        AuthenticationToken token = new UsernamePasswordToken(loginVo.getLoginName(), loginVo.getPassword());
        subject.login(token);
    }

    private void logoutIfNecessary(Subject subject) {
        subject.logout();
    }


    private String getSessionId() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);
        return (String) session.getId();
    }

    private String getNameByLogin(LoginVo loginVo) {
        User user = authService.findUserByLoginName(loginVo.getLoginName());
        return user.getName();
    }

}
