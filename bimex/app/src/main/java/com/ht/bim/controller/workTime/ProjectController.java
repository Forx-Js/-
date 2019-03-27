package com.ht.bim.controller.workTime;

import com.ht.bim.command.dto.ProjectQueryDto;
import com.ht.bim.command.dto.ProjectUserDto;
import com.ht.bim.command.service.ProjectService;
import com.ht.bim.query.service.ProjectQueryService;
import com.ht.bim.query.vo.ProjectQueryVo;
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
 * Created by dlj on 2019-3-20.
 */
@Api("项目管理")
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectQueryService projectQueryService;
    @Resource
    private ProjectService projectService;

    @ApiOperation("项目列表")
    @GetMapping("/list")
    public ListResult<ProjectQueryVo> queryProject(){
        List<ProjectQueryVo> list = projectQueryService.queryProject();
        return ListResult.success(list);
    }
    @ApiOperation("根据ID查找项目详情")
    @GetMapping("/detail")
    public ApiResult queryProjectByID(String projectID){
        ProjectQueryVo projectQueryVo= projectQueryService.queryProjectByID(projectID);
        return ApiResult.success(projectQueryVo);
    }

    @ApiOperation("添加项目")
    @PostMapping("/add")
    public ApiResult addProject(ProjectQueryDto dto){
        projectService.addProject(dto);
        return ApiResult.success();
    }
    @ApiOperation("编辑项目")
    @PostMapping("/update")
    public ApiResult updateProject(ProjectQueryDto dto){
        projectService.updateProject(dto);
        return ApiResult.success();
    }
    @ApiOperation("项目策划人员")
    @PostMapping("/add-user")
    public ApiResult addUserProject(ProjectUserDto dto){
        projectService.addUserProject(dto);
        return ApiResult.success();
    }
    @ApiOperation("给项目中的人员策划角色")
    @PostMapping("/update-user-role")
    public ApiResult updateProjectUserRole(ProjectUserDto dto){
            //根据项目id查看这个项目中是否已经存在项目经理或者部门经理
            ProjectUserDto projectUserDto = projectQueryService.queryIsPrjorBm(dto);
            if(projectUserDto==null){
                projectService.updateProjectUserRole(dto);
                return ApiResult.success();
            }else{
                return ApiResult.fail("此角色在项目已存在");
            }
    }
}
