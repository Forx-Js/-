package com.ht.bim.command.service;

import com.ht.bim.command.dao.DicDao;
import com.ht.bim.command.po.DictItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DicService {

    @Resource
    private DicDao dicDao;

    public Integer autoCalcSortNum(DictItem dic) {
        int num = dicDao.autoCalcSortNum(dic) + 1;
        return num;
    }

    public void addDicContent(DictItem dic) {
        dicDao.addDicContent(dic);
    }

    public void deleteDicContentBatch(String[] ids) {
        dicDao.deleteDicContentBatch(ids);
    }

    public void updateDicContent(DictItem dic) {
        dicDao.updateDicContent(dic);
    }
}
