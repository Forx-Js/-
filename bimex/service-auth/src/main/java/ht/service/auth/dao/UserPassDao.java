package ht.service.auth.dao;


import ht.service.auth.po.UserPassPo;

/**
 * Created by tianqi.zhang on 2018/4/3.
 */
public interface UserPassDao {

    /**
     * 修改用户信息
     * @param user
     */
    void update(UserPassPo user);

    void create(UserPassPo po);
}
