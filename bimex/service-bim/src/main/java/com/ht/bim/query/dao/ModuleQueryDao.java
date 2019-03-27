package com.ht.bim.query.dao;


import com.ht.bim.command.dto.SysModuleDto;
import com.ht.bim.query.vo.SysModuleQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dlj on 2019-1-15.
 */
public interface ModuleQueryDao {
    List<SysModuleQueryVo> queryModule(@Param("pid") String pid);

    Integer queryLimit(SysModuleDto dto);

}
