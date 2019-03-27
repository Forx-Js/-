package com.ht.bim.controller.permission;


import com.ht.bim.command.dto.SysModuleDto;
import com.ht.bim.command.dto.SysModuleInfoDto;
import com.ht.bim.command.service.ModuleService;
import com.ht.bim.query.service.ModuleQueryService;
import com.ht.bim.query.vo.SysModuleQueryVo;
import ht.appfx.common.interceptor.LoginUser;
import ht.appfx.common.interceptor.UserVo;
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
 * Created by dlj on 2019-1-15.
 */
@Api("权限设置")
@RestController
@RequestMapping("/module")
public class ModuleController {
    @Resource
    private ModuleQueryService moduleQueryService;
    @Resource
    private ModuleService moduleService;



    @ApiOperation("查询模块")
    @GetMapping("/list")
    public ListResult<SysModuleQueryVo> queryModule(SysModuleDto dto){
        if("".equals(dto.getpID())||dto.getpID() == null){
            dto.setpID("0");
        }
        List<SysModuleQueryVo> list = moduleQueryService.queryModule(dto);
        return ListResult.success(list);
    }
    @ApiOperation("添加模块")
    @PostMapping("/add")
    public ApiResult addModule(SysModuleInfoDto dto, @LoginUser UserVo user){
        dto.validate();
        moduleService.addModule(dto,user.getUserId());
        return ApiResult.success();
    }
    @ApiOperation("修改模块")
    @PostMapping("/update")
    public ApiResult updateModule(SysModuleInfoDto dto,@LoginUser UserVo user){
        moduleService.updateModule(dto,user.getUserId());
        return ApiResult.success();
    }
    @ApiOperation("删除模块")
    @PostMapping("/delete")
    public ApiResult deleteModule(String moduleID){
        moduleService.deleteModule(moduleID);
        return ApiResult.success();
    }
    @ApiOperation("修改排序号")
    @PostMapping("/update/sortnum")
    public ApiResult updateSortNum(String keyWord,@LoginUser UserVo user){
        moduleService.updateSortNum(keyWord,user.getUserId());
        return ApiResult.success();
    }
}
