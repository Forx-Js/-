package ht.service.common.dao;

import ht.service.common.po.SerialNoPo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public interface SerialNoDao {
    void lockRecord(String serialName);

    SerialNoPo find(String serialName);

    void updateNewValue(@Param("serialName") String serialName, @Param("oldValue") int oldValue,
            @Param("newValue") int newValue);

    void create(SerialNoPo po);
}
