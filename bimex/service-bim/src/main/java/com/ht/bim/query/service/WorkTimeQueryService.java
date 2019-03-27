package com.ht.bim.query.service;

import com.ht.bim.command.dto.WorkTimeDto;
import com.ht.bim.query.dao.ProjectQueryDao;
import com.ht.bim.query.dao.UserInfoQueryDao;
import com.ht.bim.query.dao.WorkTimeQueryDao;
import com.ht.bim.query.vo.WorkTimeVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dlj on 2019-3-18.
 */
@Service
public class WorkTimeQueryService {
    @Resource
    private WorkTimeQueryDao workTimeQueryDao;
    @Resource
    private ProjectQueryDao projectQueryDao;
    @Resource
    private UserInfoQueryDao userInfoQueryDao;

    public List<WorkTimeVo> queryWorkTime(WorkTimeDto dto) {
        List statusList = new ArrayList();
        if(dto.getStatus() == 3){
            statusList.add(3);
            statusList.add(4);
        }else if(dto.getStatus() == 0){
            statusList.add(1);
            statusList.add(2);
            statusList.add(3);
            statusList.add(4);
            statusList.add(5);
        }else {
            statusList.add(dto.getStatus());
        }
        dto.setStatusList(statusList);
        List<WorkTimeVo> list = workTimeQueryDao.queryWorkTime(dto);
        for (WorkTimeVo workTimeVo : list) {
            //根据项目ID找到项目名称
            String projectName = projectQueryDao.queryProjectByID(workTimeVo.getProjectID()).getProjectName();
            String name = userInfoQueryDao.querySysUserDetail(workTimeVo.getOperUserID()).getName();
            String approver = userInfoQueryDao.querySysUserDetail(workTimeVo.getApprover()).getName();
            workTimeVo.setProjectName(projectName);
            workTimeVo.setOperUserName(name);
            workTimeVo.setApprover(approver);
        }
        return list;
    }

    public WorkTimeVo queryWorkTimeByID(String iD) {
        WorkTimeVo workTimeVo = workTimeQueryDao.queryWorkTimeByID(iD);
        //根据项目ID找到项目名称
        String projectName = projectQueryDao.queryProjectByID(workTimeVo.getProjectID()).getProjectName();
        String name = userInfoQueryDao.querySysUserDetail(workTimeVo.getOperUserID()).getName();
        String approver = userInfoQueryDao.querySysUserDetail(workTimeVo.getApprover()).getName();
        workTimeVo.setProjectName(projectName);
        workTimeVo.setOperUserName(name);
        workTimeVo.setApprover(approver);
        return workTimeVo;
    }
}
