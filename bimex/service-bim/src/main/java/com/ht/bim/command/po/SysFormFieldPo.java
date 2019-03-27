package com.ht.bim.command.po;

/**
 * Created by zhong on 2019-3-17.
 */
public class SysFormFieldPo {
    private String formFieldID;
    private String formID;
    private String fieldName;
    private String fieldType;
    private String dataFuncName;
    private String fieldCaption;
    private Integer dataOrder;
    private Integer ifTableColumn;
    private Integer tableColumnOrder;

    public String getFormFieldID() {
        return formFieldID;
    }

    public void setFormFieldID(String formFieldID) {
        this.formFieldID = formFieldID;
    }

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

    public Integer getIfTableColumn() {
        return ifTableColumn;
    }

    public void setIfTableColumn(Integer ifTableColumn) {
        this.ifTableColumn = ifTableColumn;
    }

    public Integer getTableColumnOrder() {
        return tableColumnOrder;
    }

    public void setTableColumnOrder(Integer tableColumnOrder) {
        this.tableColumnOrder = tableColumnOrder;
    }
}
