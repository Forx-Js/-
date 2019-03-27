package com.ht.bim.query.dao;

import com.ht.bim.command.dto.ProjectUserDto;
import com.ht.bim.query.vo.ProjectQueryVo;
import com.ht.bim.query.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dlj on 2019-3-20.
 */
public interface ProjectQueryDao {

    List<ProjectQueryVo> queryProject();

    ProjectQueryVo queryProjectByID(String projectID);

    ProjectUserDto getProjectUserBy(@Param("projectID") String projectID,@Param("s") String s);

    ProjectUserDto queryIsPrjorBm(ProjectUserDto dto);

    SysUserQueryVo queryPrjManage(String projectID);
}
