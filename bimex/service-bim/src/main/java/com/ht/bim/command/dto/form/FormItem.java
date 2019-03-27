package com.ht.bim.command.dto.form;

import ht.framework.util.Validator;

/**
 * 与生成的 json 对应，请不要修改属性的名称
 * Created by zhong on 2019-2-5.
 */
public class FormItem {
    /**
     * input/number/textarea/switch/date/radio/...
     */
    private String type;
    /**
     * 标题
     */
    private String name;
    /**
     * 唯一标识
     */
    private String key;
    /**
     * 模型名称，与表字段对应
     */
    private String model;

    private FormItemOption options;

    public void validate() {
        Validator.notBlank(type, "表单项类型不能为空");
        Validator.notBlank(model, "表单项字段名称不能为空");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public FormItemOption getOptions() {
        return options;
    }

    public void setOptions(FormItemOption options) {
        this.options = options;
    }
}
