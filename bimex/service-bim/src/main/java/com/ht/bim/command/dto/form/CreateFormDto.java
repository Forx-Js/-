package com.ht.bim.command.dto.form;

import ht.framework.util.Validator;

/**
 * Created by zhong on 2019-2-5.
 */
public class CreateFormDto {
    private String processId;
    private String nodeId;
    private String json;

    public void validate() {
        Validator.notBlank(json, "表单json不能为空");
        Validator.notBlank(processId, "流程编号不能为空");
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
