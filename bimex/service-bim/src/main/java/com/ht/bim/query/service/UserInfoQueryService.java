package com.ht.bim.query.service;


import com.ht.bim.command.dto.SysUserQueryDto;
import com.ht.bim.query.dao.UserInfoQueryDao;
import com.ht.bim.query.vo.SysUserQueryVo;
import ht.framework.mybatis.Page;
import ht.service.auth.dao.UserDao;
import ht.service.auth.po.User;
import ht.service.common.dao.UploadFileDao;
import ht.service.common.po.UploadFilePo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dlj on 2019-1-11.
 */
@Service
public class UserInfoQueryService {
    @Resource
    private UserInfoQueryDao userInfoQueryDao;
    @Resource
    private UploadFileDao uploadFileDao;
    @Resource
    private UserDao userDao;

    public Page<SysUserQueryVo> querySysUserInfo(SysUserQueryDto dto, RowBounds rowBounds) {
        return userInfoQueryDao.querySysUserInfo(dto, rowBounds);
    }

    public SysUserQueryVo querySysUserDetail(String userID) {
        SysUserQueryVo sysUserQueryVo = userInfoQueryDao.querySysUserDetail(userID);
        if("".equals(sysUserQueryVo.getHeadURL())){

        }else {
        //获取上传附件的内容
        UploadFilePo uploadFilePo = uploadFileDao.find(sysUserQueryVo.getHeadURL());
        sysUserQueryVo.setRealName(uploadFilePo.getRealName());
        sysUserQueryVo.setFilePath(uploadFilePo.getFilePath());
        sysUserQueryVo.setFileSize(uploadFilePo.getFileSize());
        sysUserQueryVo.setCreateTime(uploadFilePo.getCreateTime());
        }
        if("".equals(sysUserQueryVo.getOperUserID())){
            sysUserQueryVo.setOperUserName("");
        }else {
            //根据用户ID查找用户姓名
            User user = userDao.find(sysUserQueryVo.getOperUserID());
            sysUserQueryVo.setOperUserName(user.getName());
        }
        return sysUserQueryVo;
    }
}
