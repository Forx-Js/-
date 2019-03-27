package com.ht.bim.command.dto;

import ht.framework.util.Validator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by dlj on 2019-3-18.
 */
@ApiModel("查询工时")
public class WorkTimeDto {
    private String iD;
    @ApiModelProperty(value = "项目ID")
    private String projectID;
    private String projectName;
    @ApiModelProperty(value = "任务描述")
    private String details;
    @ApiModelProperty(value = "审批人")
    private String approver;
    @ApiModelProperty(value = "任务时长")
    private double workTime;
    @ApiModelProperty(value = "加班时长")
    private double overTime;
    @ApiModelProperty(value = "任务时间")
    private Date openTime;
    @ApiModelProperty(value = "提交时间")
    private Date commitTime;
    @ApiModelProperty(value = "任务状态")
    private Integer status=0;
    @ApiModelProperty(value = "撤回原因")
    private String withdrawReason;
    @ApiModelProperty(value = "驳回原因")
    private String rejectReason;
    @ApiModelProperty(value = "当前登录用户id")
    private String operUserID;
    private Date operTime;
    private String statusKeyWord; //接收多个状态的时候
    private List statusList;
    private String idKeyWord;//接收多个id
    public void validateAdd() {
        Validator.notBlank(projectID, "项目ID不能位空");
        Validator.notBlank(approver, "审批人不能位空");
        Validator.notBlank(operUserID, "填报人ID不能位空");

    }
    public void validateUpdate() {
        Validator.notBlank(iD, "工时记录ID不能位空");
        Validator.notBlank(projectID, "项目ID不能位空");
        Validator.notBlank(approver, "审批人不能位空");
        Validator.notBlank(operUserID, "填报人ID不能位空");
        Validator.notBlank(status.toString(), "工时状态不能位空");

    }

    public String getIdKeyWord() {
        return idKeyWord;
    }

    public void setIdKeyWord(String idKeyWord) {
        this.idKeyWord = idKeyWord;
    }

    public List getStatusList() {
        return statusList;
    }

    public void setStatusList(List statusList) {
        this.statusList = statusList;
    }

    public String getStatusKeyWord() {
        return statusKeyWord;
    }

    public void setStatusKeyWord(String statusKeyWord) {
        this.statusKeyWord = statusKeyWord;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(double workTime) {
        this.workTime = workTime;
    }

    public double getOverTime() {
        return overTime;
    }

    public void setOverTime(double overTime) {
        this.overTime = overTime;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWithdrawReason() {
        return withdrawReason;
    }

    public void setWithdrawReason(String withdrawReason) {
        this.withdrawReason = withdrawReason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getOperUserID() {
        return operUserID;
    }

    public void setOperUserID(String operUserID) {
        this.operUserID = operUserID;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }
}
