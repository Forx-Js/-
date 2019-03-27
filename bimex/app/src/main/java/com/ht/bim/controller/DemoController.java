package com.ht.bim.controller;

import com.ht.bim.controller.demo.DemoDto;
import com.ht.bim.controller.demo.DemoQueryParam;
import com.ht.bim.controller.demo.DemoVo;
import ht.appfx.common.interceptor.LoginUser;
import ht.framework.mybatis.Page;
import ht.framework.result.ApiResult;
import ht.framework.result.ListResult;
import ht.framework.result.PageResult;
import ht.framework.util.Validator;
import ht.service.auth.po.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhong on 2018/6/11.
 */
@Api
@RestController
@RequestMapping("demo")
public class DemoController {


    @ApiOperation("获取单个实例")
    @RequestMapping(value = "/query-single/{id}", method = RequestMethod.GET)
    public ApiResult<DemoVo> querySingle(@PathVariable("id") String id) {
        Validator.notBlank(id, "参数错误");
        DemoVo vo = new DemoVo();
        return ApiResult.success(vo);
    }

    @ApiOperation("获取list")
    @RequestMapping(value = "/query-list", method = RequestMethod.POST)
    public ListResult<DemoVo> queryList(@RequestBody DemoQueryParam param) {
        List<DemoVo> list = new ArrayList<>();
        list.add(new DemoVo());
        return ListResult.success(list);
    }

    @ApiOperation("获取Page")
    @RequestMapping(value = "/query-page", method = RequestMethod.POST)
    public PageResult<DemoVo> queryPage(@RequestBody DemoQueryParam param) {
        List<DemoVo> list = new ArrayList<>();
        list.add(new DemoVo());
        Page<DemoVo> page = new Page<>(1, 20, 1, list);
        return PageResult.success(page, page.getResult());
    }

    @ApiOperation("创建")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<String> createDemo(DemoDto dto, @LoginUser User user) {
        // do somethind
        String newId = UUID.randomUUID().toString();
        return ApiResult.success(newId);
    }

    @RequiresRoles("admin")
    @RequiresPermissions("a,b,c")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiResult updateDemo(DemoDto dto) {
        // do somethind
        return ApiResult.success();
    }

    @RequestMapping(value = "/delete-single/{id}", method = RequestMethod.POST)
    public ApiResult<DemoVo> deleteSingle(@PathVariable("id") String id) {
        Validator.notBlank(id, "参数错误");
        return ApiResult.success();
    }

    /*@RequiresPermissions("m150000000000016")*/
    @RequiresPermissions("admin:query")
    @RequestMapping(value = "/admin-query", method = RequestMethod.GET)
    public ApiResult adminQuery() {
        // do somethind
        return ApiResult.success();
    }

}
