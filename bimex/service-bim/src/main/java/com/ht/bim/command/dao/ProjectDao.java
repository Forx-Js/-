package com.ht.bim.command.dao;

import com.ht.bim.command.dto.ProjectQueryDto;
import com.ht.bim.command.dto.ProjectUserDto;
import org.apache.ibatis.annotations.Param;

/**
 * Created by dlj on 2019-3-20.
 */
public interface ProjectDao {
    void addProject(ProjectQueryDto dto);

    void updateProject(ProjectQueryDto dto);


    void updateProjectUserRole(ProjectUserDto dto);

    void addUserProject(ProjectUserDto dto);
}
