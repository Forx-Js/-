package com.ht.bim.controller.dictionary;


import ht.framework.mybatis.Page;
import com.ht.bim.command.po.DictItem;
import com.ht.bim.command.service.DicService;
import com.ht.bim.query.service.DicQueryService;
import com.ht.bim.query.vo.SysDicInfoQueryVo;
import ht.framework.mybatis.RowBoundsParam;
import ht.framework.result.ApiResult;
import ht.framework.result.ListResult;
import ht.framework.result.PageResult;
import ht.framework.util.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.List;

@Api
@RestController
@RequestMapping("/dic")
public class DictionaryController {

    @Resource
    private DicService dicService;

    @Resource
    private DicQueryService dicQueryService;

    @ApiOperation("查询数据字典列表")
    @GetMapping("/query-dicList")
    public ListResult<SysDicInfoQueryVo> queryDicList() {
        List<SysDicInfoQueryVo> list = dicQueryService.queryDicList();
        return ListResult.success(list);
    }

    @ApiOperation("根据条件查询客户信息")
    @GetMapping(value = "/query")
    public PageResult<DictItem> queryDicInfo(String dicCode, RowBoundsParam rowBoundsParam) {
        Validator.notBlank(dicCode, "参数错误");
        Page<DictItem> list = dicQueryService.queryDicInfo(dicCode, rowBoundsParam.getRowBounds());
        if (list.getResult() != null && list.getResult().size() > 0) {
            return PageResult.success(list, list.getResult());
        } else {
            if (rowBoundsParam.getPageNo() > 1) {
                rowBoundsParam.setPageNo(rowBoundsParam.getPageNo() - 1);
                list = dicQueryService.queryDicInfo(dicCode, rowBoundsParam.getRowBounds());
            }
            return PageResult.success(list, list.getResult());
        }
    }

    @ApiOperation("查询数据字典详情")
    @GetMapping("/queryDetail")
    public ApiResult<DictItem> queryDetail(String id) {
        Validator.notBlank(id, "参数错误");
        DictItem dictItem = dicQueryService.queryDetail(id);
        return ApiResult.success(dictItem);
    }

    @ApiOperation("添加字典内容")
    @PostMapping("/addDicContent")
    public ApiResult addDicContent(DictItem dic) {
        try {
            dic.setSortNum(dicService.autoCalcSortNum(dic));
        } catch (Exception e) {
            dic.setSortNum(1);
        }
        DecimalFormat nf = new DecimalFormat("000");
        dic.setId(dic.getDicCode() + nf.format(dic.getSortNum()));
        dicService.addDicContent(dic);
        return ApiResult.success();
    }

    @ApiOperation("批量删除")
    @DeleteMapping(value = "/deleteDicContentBatch")
    public ApiResult deleteMenuBatch(String id) {
        Validator.notBlank(id, "参数错误");
        String[] ids = id.split(",");
        dicService.deleteDicContentBatch(ids);
        return ApiResult.success();
    }

    @ApiOperation("修改字典项内容")
    @PostMapping("/updateDicContent")
    public ApiResult updateDicContent(DictItem dic) {
        dic.validate();
        dicService.updateDicContent(dic);
        return ApiResult.success();
    }
}
