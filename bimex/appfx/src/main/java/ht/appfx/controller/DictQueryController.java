package ht.appfx.controller;

import ht.framework.result.ListResult;
import ht.framework.util.Validator;
import ht.service.common.dao.DictItemDao;
import ht.service.common.po.DictItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhong on 2018/7/8.
 */
@RestController
public class DictQueryController {

    @Resource
    private DictItemDao dictItemDao;

    @GetMapping("/common/dict/{code}")
    public ListResult<DictItem> queryDictItems(@PathVariable("code") String dictCode) {
        Validator.notBlank(dictCode, "参数错误");
        List<DictItem> items = dictItemDao.findDictItems(dictCode, null);
        return ListResult.success(items);
    }
}
