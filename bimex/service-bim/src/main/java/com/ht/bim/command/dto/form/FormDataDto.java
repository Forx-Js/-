package com.ht.bim.command.dto.form;

import ht.framework.util.Validator;

/**
 * Created by zhong on 2019-2-5.
 */
public class FormDataDto {
    private String runtimeID;
    private String formConfigID;
    private String dataJson;

    public void validate() {
        Validator.notBlank(runtimeID, "流程编号不能为空");
        Validator.notBlank(formConfigID, "配置编号不能为空");
        Validator.notBlank(dataJson, "数据json不能为空");
    }

    public String getRuntimeID() {
        return runtimeID;
    }

    public void setRuntimeID(String runtimeID) {
        this.runtimeID = runtimeID;
    }

    public String getFormConfigID() {
        return formConfigID;
    }

    public void setFormConfigID(String formConfigID) {
        this.formConfigID = formConfigID;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
