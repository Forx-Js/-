package ht.appfx.common.shiro;

import ht.framework.beans.BeanFactory;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhong on 2018-9-3.
 */
public class PasswordUtils {

    public static String[] hashPassword(String password) {
        RetryLimitHashedCredentialsMatcher matcher = BeanFactory.getBean(RetryLimitHashedCredentialsMatcher.class);
        String saltString = RandomStringUtils.randomAlphanumeric(16);
        ByteSource salt = ByteSource.Util.bytes(saltString);
        SimpleHash hash = new SimpleHash(matcher.getHashAlgorithmName(), password, salt, matcher.getHashIterations());
        String hashedPassword = Hex.encodeHexString(hash.getBytes());
        List<String> list = new ArrayList<>();
        list.add(hashedPassword);
        list.add(saltString);
        return list.toArray(new String[0]);
    }
}
