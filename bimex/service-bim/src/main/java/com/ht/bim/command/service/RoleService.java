package com.ht.bim.command.service;


import com.ht.bim.command.dao.RoleDao;
import com.ht.bim.command.dto.SysUserRoleInfoDto;
import com.ht.bim.query.vo.SysRoleQueryVo;
import ht.service.common.service.SerialNoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dlj on 2018-12-11.
 */
@Service
public class RoleService {
    @Resource
    private RoleDao roleDao;
    @Resource
    private SerialNoService serialNoService;  //生成id
    public void addRole(SysRoleQueryVo dto) {
        dto.setRoleID(serialNoService.newId());
        roleDao.addRole(dto);
    }

    public void updateRole(SysRoleQueryVo dto) {
        roleDao.updateRole(dto);
    }

    public void deleteRole(String roleID) {
        roleDao.deleteRole(roleID);
    }

    public void adduserRole(SysUserRoleInfoDto dto) {
        String[] split = dto.getKeyWord().split(",");
        for (String s : split) {
            roleDao.adduserRole(dto.getRoleID(),s);
        }
    }

    public void deleteUserRole(String iD) {
        roleDao.deleteUserRole(iD);
    }
}
