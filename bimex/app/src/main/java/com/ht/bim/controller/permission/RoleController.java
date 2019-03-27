package com.ht.bim.controller.permission;


import com.ht.bim.command.dto.SysUserRoleInfoDto;
import com.ht.bim.command.dto.permission.SaveRolePermissionDto;
import com.ht.bim.command.service.ModuleService;
import com.ht.bim.command.service.RoleService;
import com.ht.bim.query.service.RoleQueryService;
import com.ht.bim.query.vo.SysRoleQueryVo;
import com.ht.bim.query.vo.SysUserRoleQueryVo;
import ht.framework.result.ApiResult;
import ht.framework.result.ListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dlj on 2018-12-3.
 */
@Api("角色设置")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleQueryService roleQueryService;
    @Resource
    private RoleService roleService;
    @Resource
    private ModuleService moduleService;

    @ApiOperation("查询系统角色跟项目角色")
    @GetMapping("/list")
    public ListResult<SysRoleQueryVo> queryRoles(int type) {
        List<SysRoleQueryVo> list = roleQueryService.queryRoles(type);
        return ListResult.success(list);
    }

    @ApiOperation("添加角色")
    @PostMapping("/add")
    public ApiResult addRole(SysRoleQueryVo dto) {
        roleService.addRole(dto);
        return ApiResult.success();
    }

    @ApiOperation("修改角色")
    @PostMapping("/update")
    public ApiResult updateRole(SysRoleQueryVo dto) {
        roleService.updateRole(dto);
        return ApiResult.success();
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete")
    public ApiResult deleteRole(String roleID) {
        roleService.deleteRole(roleID);
        return ApiResult.success();
    }

    @ApiOperation("查询用户角色信息")
    @GetMapping("/users")
    public ListResult<SysUserRoleQueryVo> queryUserRoles(SysUserRoleInfoDto dto) {
        List<SysUserRoleQueryVo> list = roleQueryService.queryUserRoles(dto);
        return ListResult.success(list);
    }

    @ApiOperation("分配人员")
    @PostMapping("/add-user")
    public ApiResult adduserRole(SysUserRoleInfoDto dto) {
        roleService.adduserRole(dto);
        return ApiResult.success();
    }

    @ApiOperation("删除人员")
    @PostMapping("/delete-user")
    public ApiResult deleteUserRole(String iD) {
        roleService.deleteUserRole(iD);
        return ApiResult.success();
    }

    @ApiOperation("给角色设置权限")
    @PostMapping("/update-modules")
    public ApiResult updateModules(SaveRolePermissionDto dto) {
        dto.validate();
        moduleService.saveAllRoleModules(dto);
        return ApiResult.success();
    }

    @ApiOperation("给角色关联模块")
    @PostMapping("/update-module")
    public ApiResult addModuleByRole(String roleID, String data) {
        moduleService.addModuleByRole(roleID, data);
        return ApiResult.success();
    }
}
