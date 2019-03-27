package com.ht.bim.command.dao;


import com.ht.bim.command.dto.SysUserInfo;

/**
 * Created by dlj on 2019-1-14.
 */
public interface UserInfoDao {
    void addSysUserInfo(SysUserInfo dto);

    void updateSysUserInfo(SysUserInfo dto);

    void delSysUserInfo(String userID);
}
