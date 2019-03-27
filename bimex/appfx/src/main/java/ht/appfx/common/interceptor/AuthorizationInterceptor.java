package ht.appfx.common.interceptor;

import ht.framework.beans.BeanFactory;
import ht.framework.exception.ExceptionHandler;
import ht.framework.exception.ValidateException;
import ht.framework.result.ApiResult;
import ht.service.auth.po.DbSession;
import ht.service.auth.service.AuthService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String TOKEN_KEY = "token";
    public static final String SESSION = "_SESSION_";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }

        // 如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }

        // 从header中获取token
        String token = request.getHeader("token");
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        // token为空
        if (StringUtils.isBlank(token)) {
            ApiResult apiResult = ApiResult.fail("token不能为空!");
            apiResult.setResult("1000");
            response.getWriter().print(JSONObject.fromObject(apiResult));
            return false;
        }

        // 查询token信息
        AuthService authService = BeanFactory.getBean(AuthService.class);
        DbSession session = authService.findSession(token);
        if (session == null) {
            ApiResult apiResult = ApiResult.fail("token失效，请重新登录!");
            apiResult.setResult("1001");
            response.getWriter().print(JSONObject.fromObject(apiResult));
            return false;
        }
        try {
            validate(session);
        } catch (ValidateException ex) {
            ApiResult apiResult = ApiResult.fail(ExceptionHandler.getErrorMessage(ex, "用户状态异常请重新登录"));
            apiResult.setResult("1000");
            response.getWriter().print(JSONObject.fromObject(apiResult));
            return false;
        }
        // 设置token到request里，后续根据token，获取用户信息
        request.setAttribute(TOKEN_KEY, token);
        resolveSessionUser(request, session);
        return true;
    }

    private void resolveSessionUser(HttpServletRequest request, DbSession session) {
        request.setAttribute(SESSION, session);
    }

    private void validate(DbSession session) {

    }
}