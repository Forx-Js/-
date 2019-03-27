package com.ht.bim.command.dao;


import com.ht.bim.query.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by dlj on 2018-12-11.
 */
public interface RoleDao {
    void addRole(SysRoleQueryVo dto);

    void updateRole(SysRoleQueryVo dto);

    void deleteRole(String roleID);


    void deleteUserRole(String iD);

    void adduserRole(@Param("roleID") String roleID, @Param("s") String s);
}
