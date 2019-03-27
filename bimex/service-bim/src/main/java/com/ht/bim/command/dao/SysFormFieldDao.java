package com.ht.bim.command.dao;


import com.ht.bim.command.po.SysFormFieldPo;

/**
 * Created by dlj on 2019-1-17.
 */
public interface SysFormFieldDao {

    void create(SysFormFieldPo po);

    void update(SysFormFieldPo po);

    void delete(String formFieldID);

    SysFormFieldPo find(String formFieldID);
}
