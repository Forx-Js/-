package com.ht.bim.query.dao;


import com.ht.bim.query.vo.ProcessFormConfigVo;
import org.apache.ibatis.annotations.Param;

public interface ProcessFormConfigQueryDao {

    ProcessFormConfigVo findByKeyFields(@Param("processID") String processID, @Param("nodeID") String nodeID);

    ProcessFormConfigVo find(String id);

}
