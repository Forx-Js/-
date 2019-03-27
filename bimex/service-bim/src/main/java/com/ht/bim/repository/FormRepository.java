package com.ht.bim.repository;

import com.ht.bim.command.po.SysFormJsonPo;
import com.ht.bim.constant.MongoCollectionNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2019-2-23.
 */
@Service
public class FormRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveJson(SysFormJsonPo po) {
        mongoTemplate.save(po, MongoCollectionNames.FORM_JSON);
    }
}
