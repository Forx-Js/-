package com.ht.bim.query.dao;


import com.ht.bim.command.po.DictItem;
import com.ht.bim.query.vo.SysDicInfoQueryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DicQueryDao {

    List<SysDicInfoQueryVo> queryDicList();

    ht.framework.mybatis.Page<DictItem> queryDicInfo(@Param("dicCode") String dicCode, RowBounds rowBounds);

    DictItem queryDetail(String id);
}
