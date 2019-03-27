package com.ht.bim.command.dao;

import com.ht.bim.command.po.ProcessFormConfigPo;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhong
 */
public interface ProcessFormConfigDao {
    ProcessFormConfigPo find(String formConfigID);

    void create(ProcessFormConfigPo po);

    int update(ProcessFormConfigPo po);

    int delete(String formConfigID);

    ProcessFormConfigPo findByKeyField(@Param("processID") String processID, @Param("nodeID") String nodeID);
}
