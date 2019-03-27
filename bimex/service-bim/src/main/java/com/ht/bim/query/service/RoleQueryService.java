package com.ht.bim.query.service;


import com.ht.bim.command.dto.SysUserRoleInfoDto;
import com.ht.bim.constant.RoleTypes;
import com.ht.bim.query.dao.RoleQueryDao;
import com.ht.bim.query.vo.SysRoleQueryVo;
import com.ht.bim.query.vo.SysUserRoleQueryVo;
import ht.framework.exception.ValidateException;
import ht.framework.util.Validator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dlj on 2018-12-11.
 */
@Service
public class RoleQueryService {
    @Resource
    private RoleQueryDao roleQueryDao;

    public List<SysRoleQueryVo> queryRoles(int type) {
        if (!RoleTypes.isEffectRoleType(type)) {
            throw new ValidateException("参数错误，不是正确的角色类型");
        }
        return roleQueryDao.queryRoles(type);
    }

    public List<SysUserRoleQueryVo> queryUserRoles(SysUserRoleInfoDto dto) {
        Validator.notNull(dto, "查询参数不能为空");
        dto.validate();
        return roleQueryDao.queryRoleUsers(dto);
    }
}
