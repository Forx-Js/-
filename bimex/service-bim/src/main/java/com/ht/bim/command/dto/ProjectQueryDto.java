package com.ht.bim.command.dto;

import ht.framework.util.Validator;

/**
 * Created by dlj on 2019-3-20.
 */
public class ProjectQueryDto {
    private String projectID;
    private String projectName;
    private String remark;
    private Integer status;
    public void validateAdd() {
        Validator.notBlank(projectName, "项目名称不能位空");
        Validator.notBlank(remark, "项目描述不能位空");
        Validator.notBlank(status.toString(), "项目状态不能位空");
    }
    public void validateUpdate() {
        Validator.notBlank(projectID, "项目ID不能位空");
        Validator.notBlank(projectName, "项目名称不能位空");
        Validator.notBlank(remark, "项目描述不能位空");
        Validator.notBlank(status.toString(), "项目状态不能位空");
    }
    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
