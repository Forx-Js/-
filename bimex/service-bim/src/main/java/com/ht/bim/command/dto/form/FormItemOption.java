package com.ht.bim.command.dto.form;

/**
 * 与生成的 json 对应，请不要修改属性的名称
 * Created by zhong on 2019-2-5.
 */
public class FormItemOption {

    private String dataType;
    private String remoteFunc;
    private String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRemoteFunc() {
        return remoteFunc;
    }

    public void setRemoteFunc(String remoteFunc) {
        this.remoteFunc = remoteFunc;
    }
}
