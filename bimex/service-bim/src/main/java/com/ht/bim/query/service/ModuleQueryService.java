package com.ht.bim.query.service;


import com.ht.bim.constant.ModuleOperationLimits;
import com.ht.bim.query.dao.ModuleQueryDao;
import com.ht.bim.command.dto.SysModuleDto;
import com.ht.bim.query.vo.SysModuleQueryVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dlj on 2019-1-15.
 */
@Service
public class ModuleQueryService {
    @Resource
    private ModuleQueryDao moduleQueryDao;

    public List<SysModuleQueryVo> queryModule(SysModuleDto dto) {
        List<SysModuleQueryVo> modules = moduleQueryDao.queryModule(dto.getpID());
        if (CollectionUtils.isEmpty(modules)) {
            return null;
        }
        for (SysModuleQueryVo vo : modules) {
            dto.setModuleID(vo.getModuleID());
            //根据菜单ID和角色ID查询是否可见 如果没有的话就默认显示不可见不可编辑
            Integer limit = moduleQueryDao.queryLimit(dto);
            if (limit != null) {
                vo.setLimit(limit);
            } else {
                vo.setLimit(ModuleOperationLimits.NOT_VIEW);
            }
        }
        for (SysModuleQueryVo vo : modules) {
            dto.setpID(vo.getModuleID());
            vo.setSysModuleQueryVo(queryModule(dto));
        }
        return modules;
    }
}
