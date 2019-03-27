package com.ht.bim.command.dto;

/**
 * 人员信息查询条件
 */
public class SysUserQueryDto {
    private String keyWord;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
