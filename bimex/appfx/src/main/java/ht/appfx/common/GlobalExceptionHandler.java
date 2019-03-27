package ht.appfx.common;

import ht.framework.result.ApiResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;

/**
 * @author zhong
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${debug-msg}")
    private boolean showDebugMessage;

    @ExceptionHandler(value = {UnauthenticatedException.class, AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResult authenticationErrorHandler(AuthenticationException ex) throws UnsupportedEncodingException {
        logger.debug("controller发生异常", ex);
        String errorMessage = "未认证";
        return ApiResult.fail(errorMessage);
    }

    @ExceptionHandler(value = {UnauthorizedException.class, AuthorizationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResult unauthorizedErrorHandler(UnauthorizedException ex) throws UnsupportedEncodingException {
        logger.debug("controller发生异常", ex);
        String errorMessage = "权限不足";
        return ApiResult.fail(errorMessage);
    }

    @ExceptionHandler(value = {Exception.class})
    public ApiResult defaultErrorHandler(Exception ex) throws UnsupportedEncodingException {
        logger.debug("controller发生异常", ex);
        String errorMessage = showDebugMessage ?
                ex.getMessage() :
                ht.framework.exception.ExceptionHandler.getErrorMessage(ex);
        return ApiResult.fail(errorMessage);
    }
}
