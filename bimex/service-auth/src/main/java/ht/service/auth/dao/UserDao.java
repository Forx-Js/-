package ht.service.auth.dao;

import ht.service.auth.po.DbSession;
import ht.service.auth.po.User;
import ht.service.auth.po.UserPo;
import ht.service.auth.po.UserSecurityToken;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public interface UserDao {

    User find(String userId);

    User findByName(String loginName);

    UserSecurityToken findSecurityToken(String userId);

    UserSecurityToken findSecurityTokenNew(String userId);

    /**
     * 查询所有用户
     *
     * @return
     * @throws
     * @Title: findAll
     */
    List<User> findAll();

    void createSession(DbSession session);
    void updateSession(DbSession sessionInDB);

    void revokeSession(String token);

    DbSession findSession(@Param("token") String token);

    List<String> findUserPosition(String userId);

    void updateSecurityToken(UserSecurityToken token);

    void openWebLogin(@Param("userId") String userId, @Param("openTime") Date openTime);
    void openAppLogin(@Param("userId") String userId, @Param("openTime") Date openTime);

    Integer validateIfUserEnabledLogin(User user);

    User findByToken(String token);

    void create(UserPo user);

    List<User> findUserWithInvalidPassword();
}
