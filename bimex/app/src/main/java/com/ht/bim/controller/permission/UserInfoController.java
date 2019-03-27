package com.ht.bim.controller.permission;


import com.ht.bim.command.dto.SysUserInfo;
import com.ht.bim.command.dto.SysUserQueryDto;
import com.ht.bim.query.service.UserInfoQueryService;
import com.ht.bim.command.service.UserInfoService;
import com.ht.bim.query.vo.SysUserQueryVo;
import ht.appfx.common.interceptor.LoginUser;
import ht.appfx.common.interceptor.UserVo;
import ht.framework.mybatis.Page;
import ht.framework.mybatis.RowBoundsParam;
import ht.framework.result.ApiResult;
import ht.framework.result.PageResult;
import ht.framework.util.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by dlj on 2019-1-11.
 */
@Api("人员")
@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Resource
    private UserInfoQueryService userInfoQueryService;
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("根据条件查询人员信息")
    @GetMapping(value = "/list")
    public PageResult<SysUserQueryVo> querySysUserInfo(SysUserQueryDto dto, RowBoundsParam rowBoundsParam) {
        Page<SysUserQueryVo> list = userInfoQueryService.querySysUserInfo(dto,rowBoundsParam.getRowBounds());

            return PageResult.success(list,list.getResult());

    }

    @ApiOperation("查看人员详情")
    @GetMapping(value = "/detail")
    public ApiResult<SysUserQueryVo> querySysUserDetail(String userID) {
        Validator.notBlank(userID, "参数错误");
        SysUserQueryVo vo = userInfoQueryService.querySysUserDetail(userID);
        return ApiResult.success(vo);
    }

    @ApiOperation("增加人员信息")
    @PostMapping(value = "/add")
    public ApiResult addSysUserInfo(SysUserInfo dto, @LoginUser UserVo user) {
        dto.validate();
        userInfoService.addSysUserInfo(dto,user.getUserId());
        return ApiResult.success();
    }

    @ApiOperation("编辑人员信息")
    @PostMapping(value = "/update")
    public ApiResult updateSysUserInfo(SysUserInfo dto, @LoginUser UserVo user) {
        dto.validate();
        userInfoService.updateSysUserInfo(dto,user.getUserId());
        return ApiResult.success();
    }

    @ApiOperation("删除人员信息")
    @PostMapping(value = "/delete")
    public ApiResult delSysUserInfo(String userID) {
        Validator.notBlank(userID, "参数错误");
        userInfoService.delSysUserInfo(userID);
        return ApiResult.success();
    }
}