package ht.appfx.controller.auth;

import ht.appfx.common.shiro.PasswordUtils;
import ht.framework.result.ApiResult;
import ht.service.auth.po.User;
import ht.service.auth.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhong on 2018-9-3.
 */
@RestController
@RequestMapping("/utils")
public class PasswordController {

    @Resource
    private AuthService authService;

    @RequiresRoles("admin11122")
    @ApiOperation("加密密码")
    @RequestMapping(value = "/process-all-pwd", method = RequestMethod.GET)
    public ApiResult<String> processAllPassword() {
        doProcessAllPassword();
        return ApiResult.success();
    }

    private void doProcessAllPassword() {
        List<User> users = authService.findUserWithInvalidPassword();
        if (CollectionUtils.isEmpty(users)) {
            return;
        }
        for (User user : users) {
            createPassword(user.getUserId());
        }
    }

    private void createPassword(String userId) {
        String[] list = PasswordUtils.hashPassword("123456");
        authService.createPassword(userId, list[0], list[1]);
    }
}
