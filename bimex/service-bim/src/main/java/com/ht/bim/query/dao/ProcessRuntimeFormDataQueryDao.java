package com.ht.bim.query.dao;


import com.ht.bim.query.vo.ProcessRuntimeFormDataVo;
import org.apache.ibatis.annotations.Param;

public interface ProcessRuntimeFormDataQueryDao {

    ProcessRuntimeFormDataVo findByKeyFields(@Param("runtimeID") String runtimeID,
            @Param("formConfigID") String formConfigID);

    ProcessRuntimeFormDataVo find(String id);

}
