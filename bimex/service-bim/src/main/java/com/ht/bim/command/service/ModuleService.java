package com.ht.bim.command.service;


import com.ht.bim.command.dao.ModuleDao;
import com.ht.bim.command.dto.SysModuleDto;
import com.ht.bim.command.dto.SysModuleInfoDto;
import com.ht.bim.command.dto.permission.ModuleAccessDto;
import com.ht.bim.command.dto.permission.SaveRolePermissionDto;
import com.ht.bim.command.po.permission.SysModuleInfoPo;
import com.ht.bim.command.po.permission.SysModuleInfoWebPo;
import ht.framework.util.TimeSource;
import ht.framework.util.Validator;
import ht.service.common.service.SerialNoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dlj on 2019-1-17.
 */
@Service
public class ModuleService {
    @Resource
    private ModuleDao moduleDao;
    @Resource
    private TimeSource timeSource;
    @Resource
    private SerialNoService serialNoService;

    @Transactional(rollbackFor = Throwable.class)
    public void addModule(SysModuleInfoDto dto, String userId) {
        dto.validateCreate();
        dto.setNodeType(0);
        dto.setModuleID(serialNoService.newId());
        dto.setOperdate(timeSource.getCurrentDate());
        dto.setOperUserID(userId);
        moduleDao.addModule(dto);
        moduleDao.addModuleWeb(dto);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void updateModule(SysModuleInfoDto dto, String userId) {
        dto.validateUpdate();
        SysModuleInfoPo existPo = moduleDao.find(dto.getModuleID());
        Validator.notNull(existPo, "找不到模块");
        dto.setpID(existPo.getpID());
        dto.setNodeType(existPo.getNodeType());
        dto.setOperdate(timeSource.getCurrentDate());
        dto.setOperUserID(userId);
        moduleDao.updateModule(dto);
        createOrUpdateModuleWeb(dto);
    }

    private void createOrUpdateModuleWeb(SysModuleInfoDto dto) {
        SysModuleInfoWebPo existPo = moduleDao.findModuleWeb(dto.getModuleID());
        if (existPo == null) {
            moduleDao.addModuleWeb(dto);
        } else {
            moduleDao.updateModuleWeb(dto);
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteModule(String moduleID) {
        Validator.notBlank(moduleID, "模块编号不能为空");
        moduleDao.deleteModule(moduleID);
        moduleDao.deleteModuleWeb(moduleID);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void addModuleByRole(String roleId, String keyWord) {
        List<SysModuleDto> list = buildPermissions(roleId, keyWord);
        moduleDao.deleteRoleModules(roleId);
        for (SysModuleDto po : list) {
            po.validateCreate();
            moduleDao.addModuleByRole(po);
        }
    }

    /**
     * 数据格式：moduleID,limit|moduleID,limit|moduleID,limit|...|
     *
     * @param roleId
     * @param keyWord
     * @return
     */
    private List<SysModuleDto> buildPermissions(String roleId, String keyWord) {
        List<SysModuleDto> list = new ArrayList<>();
        String[] items = keyWord.split("\\|");
        for (String s : items) {
            String[] tokens = s.split(",");
            SysModuleDto dto = new SysModuleDto();
            dto.setRoleID(roleId);
            dto.setModuleID(tokens[0]);
            dto.setLimit(tokens[1]);
            list.add(dto);
        }
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void saveAllRoleModules(SaveRolePermissionDto dto) {
        dto.validate();
        moduleDao.deleteRoleModules(dto.getRoleID());
        if (CollectionUtils.isEmpty(dto.getModules())) {
            return;
        }
        for (ModuleAccessDto accessDto : dto.getModules()) {
            createPermission(dto.getRoleID(), accessDto);
        }
    }

    private void createPermission(String roleID, ModuleAccessDto accessDto) {
        SysModuleDto po = new SysModuleDto();
        po.setRoleID(roleID);
        po.setModuleID(accessDto.getModuleID());
        po.setLimit(new Integer(accessDto.getLimit()).toString());
        moduleDao.addModuleByRole(po);
    }

    public void updateSortNum(String keyWord, String userId) {
        List<SysModuleInfoDto> list = new ArrayList<>();
        String[] items = keyWord.split("\\|");
        for (String s : items) {
            String[] tokens = s.split(",");
            SysModuleInfoDto dto = new SysModuleInfoDto();
            dto.setModuleID(tokens[0]);
            dto.setSortNum(Integer.parseInt(tokens[1]));
            list.add(dto);
        }
        for (SysModuleInfoDto dto : list) {
            moduleDao.updateSortNum(dto);
        }
    }
}
