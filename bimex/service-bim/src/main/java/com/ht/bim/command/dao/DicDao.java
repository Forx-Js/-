package com.ht.bim.command.dao;

import com.ht.bim.command.po.DictItem;
import org.apache.ibatis.annotations.Param;

public interface DicDao {
    
    int autoCalcSortNum(DictItem dic);

    void addDicContent(DictItem dic);

    void deleteDicContentBatch(@Param("ids") String[] ids);

    void updateDicContent(@Param("dic") DictItem dic);
}
