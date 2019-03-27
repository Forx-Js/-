package com.ht.bim.controller.form;

import com.ht.bim.command.dto.form.CreateFormDto;
import com.ht.bim.command.dto.form.FormDataDto;
import com.ht.bim.command.service.FormDataService;
import com.ht.bim.command.service.FormService;
import com.ht.bim.command.service.SysFormFieldService;
import com.ht.bim.dto.CreateSysFormFieldDto;
import com.ht.bim.query.dao.ProcessFormConfigQueryDao;
import com.ht.bim.query.dao.ProcessRuntimeFormDataQueryDao;
import com.ht.bim.query.vo.ProcessFormConfigVo;
import com.ht.bim.query.vo.ProcessRuntimeFormDataVo;
import ht.appfx.common.interceptor.LoginUser;
import ht.appfx.common.interceptor.UserVo;
import ht.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author zhong
 * @date 2019-2-5
 */
@Api("流程表单服务")
@RestController
@RequestMapping("/form")
public class FormController {

    @Resource
    private FormService formService;
    @Resource
    private FormDataService formDataService;
    @Resource
    private ProcessFormConfigQueryDao processFormConfigQueryDao;
    @Resource
    private ProcessRuntimeFormDataQueryDao processRuntimeFormDataQueryDao;
    @Resource
    private SysFormFieldService sysFormFieldService;

    @ApiOperation("创建新的表单")
    @PostMapping("/config/create")
    public ApiResult<String> createForm(@RequestBody CreateFormDto dto, @LoginUser UserVo user) throws IOException {
        dto.validate();
        String configId = formService.createForm(dto, user.getUserId());
        return ApiResult.success(configId);
    }

    @ApiOperation("获取信息表单")
    @GetMapping("/config/get")
    public ApiResult<ProcessFormConfigVo> query(@RequestParam String processID, @RequestParam String nodeID)
            throws IOException {
        // TODO 使用下面的代码
        // ProcessFormConfigVo vo = processFormConfigQueryDao.findByKeyFields(processID, nodeID);
        ProcessFormConfigVo vo = processFormConfigQueryDao.find("e87fc147-ddfa-4ccc-ae63-3ed7e607cb34");
        return ApiResult.success(vo);
    }

    @ApiOperation("保存表单数据")
    @PostMapping("/data/save")
    public ApiResult<String> saveFormData(@RequestBody FormDataDto dto, @LoginUser UserVo user) throws IOException {
        dto.validate();
        String dataId = formDataService.saveFormData(dto, user.getUserId());
        return ApiResult.success(dataId);
    }

    @ApiOperation("获取表单数据")
    @GetMapping("/data/get")
    public ApiResult<ProcessRuntimeFormDataVo> queryFormData(@RequestParam String runtimeID,
            @RequestParam String formConfigID)
            throws IOException {
        ProcessRuntimeFormDataVo vo = processRuntimeFormDataQueryDao.findByKeyFields(runtimeID, formConfigID);
        return ApiResult.success(vo);
    }

    @ApiOperation("保存表单结构")
    @PostMapping("/fields/create")
    public ApiResult<String> saveFormFields(@RequestBody CreateSysFormFieldDto dto) {
        dto.validate();
        sysFormFieldService.createFormField(dto.getFormId(), dto.getFields());
        return ApiResult.success();
    }

}
