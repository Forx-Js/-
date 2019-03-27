package com.ht.bim.query.service;

import com.ht.bim.command.dto.ProjectUserDto;
import com.ht.bim.query.dao.ProjectQueryDao;
import com.ht.bim.query.vo.ProjectQueryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dlj on 2019-3-20.
 */
@Service
public class ProjectQueryService {
    @Resource
    private ProjectQueryDao projectQueryDao;
    public List<ProjectQueryVo> queryProject() {
      return projectQueryDao.queryProject();
    }

    public ProjectQueryVo queryProjectByID(String projectID) {
        return projectQueryDao.queryProjectByID(projectID);
    }

    public ProjectUserDto queryIsPrjorBm(ProjectUserDto dto) {
        return projectQueryDao.queryIsPrjorBm(dto);
    }
}
