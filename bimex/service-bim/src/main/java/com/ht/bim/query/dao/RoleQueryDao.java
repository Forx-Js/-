package com.ht.bim.query.dao;



import com.ht.bim.command.dto.SysUserRoleInfoDto;
import com.ht.bim.query.vo.SysRoleQueryVo;
import com.ht.bim.query.vo.SysUserRoleQueryVo;

import java.util.List;

/**
 * Created by dlj on 2018-12-11.
 */
public interface RoleQueryDao {
    List<SysRoleQueryVo> queryRoles(int type);

    List<SysUserRoleQueryVo> queryRoleUsers(SysUserRoleInfoDto dto);
}
