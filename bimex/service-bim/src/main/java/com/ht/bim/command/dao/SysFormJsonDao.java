package com.ht.bim.command.dao;


import com.ht.bim.command.po.SysFormJsonPo;

/**
 * Created by dlj on 2019-1-17.
 */
public interface SysFormJsonDao {

    void create(SysFormJsonPo po);

    void update(SysFormJsonPo po);

    void delete(String formJsonID);

    SysFormJsonPo find(String formJsonID);
}
