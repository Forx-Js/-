package ht.service.common.dao;

import ht.service.common.po.DictItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public interface DictItemDao {
    List<DictItem> findDictItems(@Param("dictCode") String dictCode, @Param("pid") String pid);

    String findDictItemName(@Param("dictCode") String dictCode, @Param("itemId") String itemId);

    String findDictItemId(@Param("dictCode") String dictCode, @Param("itemName") String itemName);
}
