package ht.appfx.controller;

import ht.appfx.common.interceptor.LoginUser;
import ht.appfx.common.interceptor.UserVo;
import ht.framework.result.ListResult;
import ht.service.auth.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dlj on 2019-1-21.
 */
@Api("权限")
@RestController
public class PermissionController {
    @Resource
    private AuthService authService;

    @ApiOperation("查询当前用户的所有权限")
    @RequestMapping("/permission/my-permissions")
    public ListResult<String> queryPermissions(@LoginUser UserVo user) {
        List<String> permissions = authService.findUserPermissions(user.getUserId());
        return ListResult.success(permissions);
    }
}
