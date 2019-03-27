package ht.service.auth.dao;

import java.util.List;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public interface UserRoleDao {


    List<String> fileUserPermissions(String userId);

    List<String> findUserRoles(String userId);
}
