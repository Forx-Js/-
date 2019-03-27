package com.ht.bim.dto;

import com.ht.bim.command.dto.SysFormFieldDto;

import java.util.List;

/**
 * Created by zhong on 2019-3-17.
 */
public class CreateSysFormFieldDto {
    private String formId;
    private List<SysFormFieldDto> fields;

    public void validate() {

    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<SysFormFieldDto> getFields() {
        return fields;
    }

    public void setFields(List<SysFormFieldDto> fields) {
        this.fields = fields;
    }

}
