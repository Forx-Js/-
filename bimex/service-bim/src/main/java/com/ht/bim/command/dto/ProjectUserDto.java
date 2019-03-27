package com.ht.bim.command.dto;

/**
 * Created by dlj on 2019-3-20.
 */
public class ProjectUserDto {
    private String iD;
    private String projectID;
    private String userID;
    private Integer isPrjManage;
    private Integer isBMManage;

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getIsPrjManage() {
        return isPrjManage;
    }

    public void setIsPrjManage(Integer isPrjManage) {
        this.isPrjManage = isPrjManage;
    }

    public Integer getIsBMManage() {
        return isBMManage;
    }

    public void setIsBMManage(Integer isBMManage) {
        this.isBMManage = isBMManage;
    }
}