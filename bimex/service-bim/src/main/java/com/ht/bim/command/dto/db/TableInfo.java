package com.ht.bim.command.dto.db;

import ht.framework.util.Validator;

import java.util.List;

/**
 * Created by zhong on 2019-2-5.
 */
public class TableInfo {

    private String tableName;
    private List<ColumnInfo> columns;

    public void validate() {
        Validator.notNull(columns, "字段信息不能为空");
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }
}
