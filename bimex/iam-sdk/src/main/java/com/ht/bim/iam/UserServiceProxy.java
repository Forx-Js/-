package com.ht.bim.iam;

import com.ht.bim.iam.core.IamClient;
import com.ht.bim.iam.model.RealNameParam;
import com.ht.bim.iam.model.UserIdParam;
import com.ht.bim.iam.model.UserInfo;
import com.ht.bim.iam.model.UserInfo2;
import com.ht.bim.iam.model.UserNameParam;
import com.ht.bim.iam.model.UserNamesParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zeng.ling on 2019-3-26.
 */
@Service
public class UserServiceProxy {

    @Autowired
    private IamClient iamClient;

    public UserInfo getUserById(String userId) {
        UserIdParam param = new UserIdParam();
        param.setUserId(userId);
        return iamClient.post("/public/v1/user/getUserById", param);
    }

    public UserInfo getUserByUsername(String userName) {
        UserNameParam param = new UserNameParam();
        param.setUserName(userName);
        return iamClient.post("/public/v1/user/getUserByUsername", param);
    }

    public List<UserInfo> getUserListByUsernames(List<String> userNames) {
        if (CollectionUtils.isEmpty(userNames)) {
            return null;
        }
        UserNamesParam param = new UserNamesParam();
        param.setUserNames(userNames);
        return iamClient.post("/public/v1/user/getUserListByUsernames", param);
    }

    public List<UserInfo2> getUserListByRealName(String realName) {
        RealNameParam param = new RealNameParam();
        param.setRealName(realName);
        return iamClient.post("/public/v1/user/getUserListByRealName", param);
    }
}
