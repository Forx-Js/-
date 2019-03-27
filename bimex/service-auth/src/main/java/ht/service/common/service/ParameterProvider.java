package ht.service.common.service;

import ht.framework.exception.ValidateException;
import ht.service.common.dao.ParamDao;
import ht.service.common.po.ParamPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhong on 2018/1/11.
 */
@Service
public class ParameterProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterProvider.class);

    @Resource
    private ParamDao paramDao;

    public String uploadRootDirectory() {
        return findByName("upload-file-root-dir");
    }

    public boolean enablePush() {
        return StringUtils.equals("1", findByName("enablePush"));
    }

    public String jPushUrl() {
        return findByName("jPushUrl");
    }

    public String jPushAppKey() {
        return findByName("jPushAppKey");
    }

    public String jPushAppSecret() {
        return findByName("jPushAppSecret");
    }

    public boolean jPushToTest() {
        String value = findValue("jPushToTest");
        return StringUtils.equals(value, "1");
    }

    public List<String> getTestUsers() {
        return findValues("jPushTestUser");
    }

    public List<String> getTestDepartments() {
        return findValues("jPushTestDepartment");
    }

    private String findByName(String name) {
        ParamPo param = paramDao.findByName(name);
        if (param == null) {
            LOGGER.error("参数 {} 不存在", name);
            throw new ValidateException("参数不存在");
        }
        return param.getValue();
    }

    private String findValue(String name) {
        ParamPo param = paramDao.findByName(name);
        return param == null ? null : param.getValue();
    }

    private List<String> findValues(String name) {
        List<String> list = paramDao.findValues(name);
        return list == null ? new ArrayList<String>() : list;
    }

    public String loginMode() {
        return findByName("loginMode");
    }

    public String ldapUrl() {
        return findByName("ldapUrl");
    }

    public String ldapDomain() {
        return findByName("ldapDomain");
    }

    public boolean supportDomainLogin() {
        return false;
    }
}
