package com.ht.bim.iam.core;

import com.alibaba.druid.util.StringUtils;
import ht.framework.exception.ValidateException;
import ht.framework.util.Validator;

/**
 * Created by zeng.ling on 2019-3-26.
 */
public class IamResult<T> {
    public static final String SUCCESS = "1";

    private IamResponseCode respCode;
    private T data;

    public T payload() {
        Validator.notNull(respCode, "响应报文错误");
        if (!StringUtils.equals(respCode.getCode(), SUCCESS)) {
            throw new ValidateException(ErrorMessages.translate(respCode.getCode()));
        }
        return data;
    }

    public IamResponseCode getRespCode() {
        return respCode;
    }

    public void setRespCode(IamResponseCode respCode) {
        this.respCode = respCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
