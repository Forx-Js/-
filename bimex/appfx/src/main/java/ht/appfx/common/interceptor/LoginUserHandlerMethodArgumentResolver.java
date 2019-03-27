package ht.appfx.common.interceptor;

import ht.appfx.common.security.JwtToken;
import ht.service.auth.po.User;
import ht.service.auth.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private AuthService authService;
    @Resource
    private JwtToken jwtToken;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserVo.class) && parameter.hasParameterAnnotation(
                LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request,
            WebDataBinderFactory factory) throws Exception {
        // 获取用户TOKEN
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        String token = servletRequest.getHeader(AuthorizationInterceptor.TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            Object object = servletRequest.getAttribute(AuthorizationInterceptor.TOKEN_KEY);
            if (object == null) {
                return null;
            }
            token = object.toString();
        }
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String loginName = jwtToken.getPrincipal(token);
        // 获取用户信息
        User user = authService.findUserByLoginName(loginName);
        UserVo userVo = convertToUserVo(user);
        return userVo;
    }

    private UserVo convertToUserVo(User user) {
        UserVo userVo = new UserVo();
        userVo.setUserId(user.getUserId());
        userVo.setUserName(user.getUserName());
        userVo.setName(user.getName());
        return userVo;
    }

    private UserVo getDemoUser() {
        UserVo vo = new UserVo();
        vo.setUserId("10000");
        vo.setUserName("Sophia");
        return vo;
    }
}