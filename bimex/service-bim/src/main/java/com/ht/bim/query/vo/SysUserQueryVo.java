package com.ht.bim.query.vo;

import java.util.Date;

/**
 * 人员信息展示内容
 */
public class SysUserQueryVo {
    private String userID;
    private String userName;
    private String passWord;
    private String staffID;
    private String name;
    private String sex;
    private String nation;
    private Date birthDay;
    private String nativePlace;
    private String IDCard;
    private String PhoneNum;
    private String TelNum;
    private String Address;
    private Integer Status;
    private Integer Type;
    private String WorkTypeCode;
    private String WorkType;
    private String HeadURL;
    private String PrjID;
    private Byte IfDelete;
    private String domainAccount;

    //附件信息
    private String realName;
    private String filePath;
    private Date createTime;
    private String operUserName;
    private String operUserID;
    private String fileSize;

    public String getOperUserID() {
        return operUserID;
    }

    public void setOperUserID(String operUserID) {
        this.operUserID = operUserID;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getTelNum() {
        return TelNum;
    }

    public void setTelNum(String telNum) {
        TelNum = telNum;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getWorkTypeCode() {
        return WorkTypeCode;
    }

    public void setWorkTypeCode(String workTypeCode) {
        WorkTypeCode = workTypeCode;
    }

    public String getWorkType() {
        return WorkType;
    }

    public void setWorkType(String workType) {
        WorkType = workType;
    }

    public String getHeadURL() {
        return HeadURL;
    }

    public void setHeadURL(String headURL) {
        HeadURL = headURL;
    }

    public String getPrjID() {
        return PrjID;
    }

    public void setPrjID(String prjID) {
        PrjID = prjID;
    }

    public Byte getIfDelete() {
        return IfDelete;
    }

    public void setIfDelete(Byte ifDelete) {
        IfDelete = ifDelete;
    }

    public String getDomainAccount() {
        return domainAccount;
    }

    public void setDomainAccount(String domainAccount) {
        this.domainAccount = domainAccount;
    }
}
