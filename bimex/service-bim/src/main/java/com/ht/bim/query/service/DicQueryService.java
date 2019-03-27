package com.ht.bim.query.service;


import com.ht.bim.command.po.DictItem;
import com.ht.bim.query.dao.DicQueryDao;
import com.ht.bim.query.vo.SysDicInfoQueryVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DicQueryService {

    @Resource
    private DicQueryDao dicQueryDao;

    public List<SysDicInfoQueryVo> queryDicList() {
        return dicQueryDao.queryDicList();
    }

    public ht.framework.mybatis.Page<DictItem> queryDicInfo(String dicCode, RowBounds rowBounds) {
        return dicQueryDao.queryDicInfo(dicCode, rowBounds);
    }

    public DictItem queryDetail(String id) {
        return dicQueryDao.queryDetail(id);
    }
}
