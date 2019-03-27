package com.ht.bim.command.service;

import com.ht.bim.command.dao.ProjectDao;
import com.ht.bim.command.dto.ProjectQueryDto;
import com.ht.bim.command.dto.ProjectUserDto;
import com.ht.bim.query.dao.ProjectQueryDao;
import ht.service.common.service.SerialNoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dlj on 2019-3-20.
 */
@Service
public class ProjectService {
    @Resource
    private ProjectDao projectDao;
    @Resource
    private SerialNoService serialNoService;  //生成id
    @Resource
    private ProjectQueryDao projectQueryDao;

    public void addProject(ProjectQueryDto dto) {
        dto.validateAdd();
        dto.setProjectID(serialNoService.newId());
        projectDao.addProject(dto);
    }

    public void updateProject(ProjectQueryDto dto) {
        dto.validateUpdate();
        dto.setStatus(1);
        projectDao.updateProject(dto);
    }

    public void addUserProject(ProjectUserDto dto) {
        dto.setiD(serialNoService.newId());
        dto.setIsPrjManage(0);
        dto.setIsBMManage(0);
        String[] split = dto.getUserID().split(",");
        for (String s : split) {
            //通过项目ID跟用户ID查看这个人是否存在（就是已经策划过了）
            ProjectUserDto projectUserDto = projectQueryDao.getProjectUserBy(dto.getProjectID(),s);
            if(projectUserDto==null){
                dto.setUserID(s);
                projectDao.addUserProject(dto);
            }else {
            continue;
            }
        }
    }

    public void updateProjectUserRole(ProjectUserDto dto) {
        projectDao.updateProjectUserRole(dto);
    }
}
