package com.ht.bim.iam.core;

import com.alibaba.druid.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zeng.ling on 2019-3-26.
 */
public class ErrorMessages {
    private static final String DEFAULT_IAM_MESSAGE = "IAM 返回错误";
    private static Map<String, String> map;

    static {
        map = new HashMap<>();
        map.put("2000", "参数非法");
        map.put("2001", "应用不存在");
        map.put("2003", "应用未启用");
        map.put("9999", "系统异常");
    }

    public static String translate(String errorCode) {
        if (StringUtils.equals(errorCode, "1")) {
            return "成功";
        }
        if (map.containsKey(errorCode)) {
            return map.get(errorCode);
        }
        return DEFAULT_IAM_MESSAGE + ": " + errorCode;
    }

}
