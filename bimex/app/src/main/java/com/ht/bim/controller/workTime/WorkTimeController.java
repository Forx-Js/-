package com.ht.bim.controller.workTime;

import com.ht.bim.command.dto.WorkTimeDto;
import com.ht.bim.command.service.WorkTimeService;
import com.ht.bim.query.service.WorkTimeQueryService;
import com.ht.bim.query.vo.WorkTimeVo;
import ht.appfx.common.interceptor.LoginUser;
import ht.appfx.common.interceptor.UserVo;
import ht.framework.result.ApiResult;
import ht.framework.result.ListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by dlj on 2019-3-18.
 */
@Api("工时填报")
@RestController
@RequestMapping("/worktime")
public class WorkTimeController {
    @Resource
    private WorkTimeQueryService workTimeQueryService;
    @Resource
    private WorkTimeService workTimeService;

    @ApiOperation("查询工时")
    @GetMapping("/list")
    public ListResult<WorkTimeVo> queryWorkTime(WorkTimeDto dto){
        List<WorkTimeVo> list = workTimeQueryService.queryWorkTime(dto);
        return ListResult.success(list);
    }
    @ApiOperation("根据id查询工时")
    @GetMapping("/detail")
    public ApiResult<WorkTimeVo> queryWorkTimeByID(String iD){
        WorkTimeVo workTimeVo = workTimeQueryService.queryWorkTimeByID(iD);
        return ApiResult.success(workTimeVo);
    }

    @ApiOperation("新增工时")
    @PostMapping("/add")
    public ApiResult addWorkTime(WorkTimeDto dto, @LoginUser UserVo user){
        workTimeService.addWorkTime(dto,user.getUserId());
        return ApiResult.success();
    }
    @ApiOperation("编辑工时")
    @PostMapping("/update")
    public ApiResult updateWorkTime(WorkTimeDto dto, @LoginUser UserVo user){
        workTimeService.updateWorkTime(dto,user.getUserId());
        return ApiResult.success();
    }
    @ApiOperation("删除工时")
    @PostMapping("/delete")
    public ApiResult deleteWorkTime(String iD){
        workTimeService.deleteWorkTime(iD);
        return ApiResult.success();
    }
    @ApiOperation("改状态")
    @PostMapping("/update-status")
    public ApiResult updateStatus(@ApiParam(name = "keyWords", value="格式 id,reason|id,reason... 如果只有id就只传id") String keyWords, Integer status){
        workTimeService.updateStatus(keyWords,status);
        return ApiResult.success();
    }
}




