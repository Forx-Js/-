package ht.appfx;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhong on 2018-9-3.
 */
public class TestPasswordGen {

    public static String[] hashPassword(String password) {
//        RetryLimitHashedCredentialsMatcher matcher = BeanFactory.getBean(RetryLimitHashedCredentialsMatcher.class);
        String saltString = RandomStringUtils.randomAlphanumeric(16);
        ByteSource salt = ByteSource.Util.bytes(saltString);
        SimpleHash hash = new SimpleHash("SHA-256", password, salt, 1);
        String hashedPassword = Hex.encodeHexString(hash.getBytes());
        List<String> list = new ArrayList<>();
        list.add(hashedPassword);
        list.add(saltString);
        return list.toArray(new String[0]);
    }

    @Test
    public void testNormal() {
        String[] array = hashPassword("123456");
        System.out.println(array[0]);
        System.out.println(array[1]);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-256");
        AuthenticationToken token = new UsernamePasswordToken("liq", "123456");
        ByteSource salt = ByteSource.Util.bytes(array[1]);
        SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo("liq", array[0], salt,
                "test");
        boolean success = matcher.doCredentialsMatch(token, auth);
        Assert.assertTrue("密码是否匹配", success);
    }

}
