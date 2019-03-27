package ht.service.common.dto;

import java.io.Serializable;

/**
 * Created by zhong on 2017/11/28.
 */
public class XResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean success;
    private String code;
    private String message;
    private Object content;

    public XResult() {

    }


    public XResult(Object content) {
        setCode("0000");
        setContent(content);
        success = true;
    }

    public static XResult createFailResult(String message) {
        XResult result = new XResult();
        result.setCode("0001");
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

    public static XResult createSuccessResult() {
        XResult result = new XResult();
        result.setCode("0000");
        result.setSuccess(true);
        return result;
    }

    public static XResult createSuccessResult(Object content) {
        XResult result = createSuccessResult();
        result.setContent(content);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
