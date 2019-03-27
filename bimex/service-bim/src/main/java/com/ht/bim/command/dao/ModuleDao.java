package com.ht.bim.command.dao;


import com.ht.bim.command.dto.SysModuleDto;
import com.ht.bim.command.dto.SysModuleInfoDto;
import com.ht.bim.command.po.permission.SysModuleInfoPo;
import com.ht.bim.command.po.permission.SysModuleInfoWebPo;

/**
 * Created by dlj on 2019-1-17.
 */
public interface ModuleDao {
    void addModule(SysModuleInfoDto dto);

    void addModuleWeb(SysModuleInfoDto dto);

    void updateModule(SysModuleInfoDto dto);

    void updateModuleWeb(SysModuleInfoDto dto);

    void deleteModule(String moduleID);

    void deleteModuleWeb(String moduleID);

    void addModuleByRole(SysModuleDto dto);

    void updateModuleByRole(SysModuleDto dto);

    void deleteRoleModules(String roleID);

    SysModuleInfoPo find(String moduleID);

    SysModuleInfoWebPo findModuleWeb(String moduleID);

    void updateSortNum(SysModuleInfoDto dto);
}
