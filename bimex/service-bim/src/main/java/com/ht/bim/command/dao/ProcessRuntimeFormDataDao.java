package com.ht.bim.command.dao;

import com.ht.bim.command.po.ProcessRuntimeFormDataPo;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhong
 */
public interface ProcessRuntimeFormDataDao {
    ProcessRuntimeFormDataPo find(String formConfigID);

    ProcessRuntimeFormDataPo findByKeyFields(@Param("formConfigID") String formConfigID,
            @Param("runtimeID") String runtimeID);

    void create(ProcessRuntimeFormDataPo po);

    int update(ProcessRuntimeFormDataPo po);

    int delete(String formConfigID);

}
