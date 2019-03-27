package com.ht.bim.controller.app;

import com.ht.bim.command.dto.form.CreateFormDto;
import com.ht.bim.command.service.FormService;
import com.ht.bim.query.dao.ProcessFormConfigQueryDao;
import com.ht.bim.query.dao.ProcessRuntimeFormDataQueryDao;
import ht.appfx.common.interceptor.LoginUser;
import ht.appfx.common.interceptor.UserVo;
import ht.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author zhong
 * @date 2019-2-5
 */
@Api("应用服务")
@RestController
@RequestMapping("/app")
public class AppController {

    @Resource
    private FormService formService;
    @Resource
    private ProcessFormConfigQueryDao processFormConfigQueryDao;
    @Resource
    private ProcessRuntimeFormDataQueryDao processRuntimeFormDataQueryDao;

    @ApiOperation("创建新的表单")
    @PostMapping("/config/create")
    public ApiResult<String> createApp(@RequestBody CreateFormDto dto, @LoginUser UserVo user) throws IOException {
        dto.validate();
//        String configId = formService.createForm(dto, user.getUserId());
//        formService.createFormToMongo(dto, user.getUserId());
        return ApiResult.success(null);
    }

}
