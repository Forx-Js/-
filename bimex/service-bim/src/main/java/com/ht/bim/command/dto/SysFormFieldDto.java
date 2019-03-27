package com.ht.bim.command.dto;

/**
 * Created by zhong on 2019-3-17.
 */
public class SysFormFieldDto {
    private String formID;
    private String fieldName;
    private String fieldType;
    private String dataFuncName;
    private String fieldCaption;
    private Integer dataOrder;

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getDataFuncName() {
        return dataFuncName;
    }

    public void setDataFuncName(String dataFuncName) {
        this.dataFuncName = dataFuncName;
    }

    public String getFieldCaption() {
        return fieldCaption;
    }

    public void setFieldCaption(String fieldCaption) {
        this.fieldCaption = fieldCaption;
    }

    public Integer getDataOrder() {
        return dataOrder;
    }

    public void setDataOrder(Integer dataOrder) {
        this.dataOrder = dataOrder;
    }
}
