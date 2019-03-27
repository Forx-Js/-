package ht.appfx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ht.framework.exception.ValidateException;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public class LoginVo implements Serializable {
    @JsonProperty("username")
    private String loginName;
    private String password;
    /**
     * 表示登录类型，web-浏览器访问， app-app访问, 默认为web
     */
    private String clientType;

    public void validate() {
        if (StringUtils.isNotEmpty(clientType) && clientType.length() > 10) {
            throw new ValidateException("终端类型不支持");
        }
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
