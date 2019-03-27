package ht.appfx.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import ht.framework.exception.BusinessException;
import ht.framework.util.TimeSource;
import ht.framework.util.Validator;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhong on 2018/7/1.
 */
@Service
public class JwtToken {

    @Resource
    private TimeSource timeSource;

    @Value("${secret}")
    private String secret;

    @Value("${expireInterval}")
    private Long expireInterval;

    public String createToken(String principal) {
        try {
            Validator.notBlank(principal, "参数错误，用户编号不能为空");
            Date expiresDate = new Date(timeSource.getCurrentTime().getTime() + expireInterval);
            Map<String, Object> headerMap = new HashMap<>(8);
            headerMap.put("alg", "HS256");
            headerMap.put("typ", "JWT");
            String token = JWT.create().withHeader(headerMap)
                    .withClaim("iss", "Service")
                    .withClaim("aud", "APP")
                    .withClaim("user_id", principal)
                    .withIssuedAt(timeSource.getCurrentTime())
                    .withExpiresAt(expiresDate)
                    .sign(Algorithm.HMAC256(secret));
            return token;
        } catch (UnsupportedEncodingException ex) {
            throw new BusinessException(ex);
        }
    }

    public Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (UnsupportedEncodingException ex) {
            throw new UnauthenticatedException(ex);
        }
    }

    public String getPrincipal(String token) {
        Map<String, Claim> map = verifyToken(token);
        Claim claim = map.get("user_id");
        return claim.asString();
    }
}
