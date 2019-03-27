package ht.service.common.dto;

import ht.framework.exception.BusinessException;

/**
 * Created by zhong on 2017/11/29.
 */
public class XResultUtils {


    public static XResult createFailedResult(Exception ex) {
        String errMsg;
        if (ex instanceof BusinessException) {
            errMsg = ex.getMessage();
        } else {
            errMsg = "系统错误";
        }
        System.out.println(ex);
        return XResult.createFailResult(errMsg);
    }

}
