package com.ht.bim.command.service;



import com.ht.bim.command.dao.UserInfoDao;
import com.ht.bim.command.dto.SysUserInfo;
import ht.framework.util.TimeSource;
import ht.service.common.service.SerialNoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private TimeSource timeSource;  //生成时间

    @Resource
    private SerialNoService serialNoService;  //生成id

    public void addSysUserInfo(SysUserInfo dto, String userId) {
        dto.setUserID(serialNoService.newId());
        dto.setOperTime(timeSource.getCurrentTime());
        dto.setOperUserID(userId);
        userInfoDao.addSysUserInfo(dto);
    }

    public void updateSysUserInfo(SysUserInfo dto, String userId) {
        dto.setOperTime(timeSource.getCurrentTime());
        dto.setOperUserID(userId);
        userInfoDao.updateSysUserInfo(dto);
    }

    public void delSysUserInfo(String userID) {
        userInfoDao.delSysUserInfo(userID);
    }
}
