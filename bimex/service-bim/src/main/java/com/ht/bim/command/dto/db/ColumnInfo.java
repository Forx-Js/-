package com.ht.bim.command.dto.db;

/**
 * Created by zhong on 2019-2-5.
 */
public class ColumnInfo {
    private String configKey;
    private String columnName;
    /**
     * @see SupportedColumnDataTypes
     */
    private String dataType;

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
