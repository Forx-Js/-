package ht.service.auth.service;

import ht.framework.exception.ValidateException;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2018/1/29.
 */
@Service
public class PasswordRule {
    public void passwordValid(String password) {
        // TODO 密码规则
        if (password.length() < 6) {
            throw new ValidateException("密码长度不能小于6位");
        }
    }
}
