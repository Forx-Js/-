package ht.service.common.dao;

import ht.service.common.po.ParamPo;

import java.util.List;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public interface ParamDao {

    ParamPo findByName(String name);

    List<String> findValues(String name);
}
