package com.ht.bim.query.dao;


import com.ht.bim.command.dto.SysUserQueryDto;
import com.ht.bim.query.vo.SysUserQueryVo;
import ht.framework.mybatis.Page;
import org.apache.ibatis.session.RowBounds;

/**
 * Created by dlj on 2019-1-11.
 */
public interface UserInfoQueryDao {
    Page<SysUserQueryVo> querySysUserInfo(SysUserQueryDto dto, RowBounds rowBounds);

    SysUserQueryVo querySysUserDetail(String userID);
}
