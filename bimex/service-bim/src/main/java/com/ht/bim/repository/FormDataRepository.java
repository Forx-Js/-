package com.ht.bim.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ht.bim.constant.MongoCollectionNames;
import ht.framework.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by zhong on 2019-3-17.
 */
@Service
public class FormDataRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public String createData(String formID, String dataJson) {
        Validator.notBlank(formID, "表单编号不能为空");
        Validator.notBlank(dataJson, "数据不能为空");
        String id = UUID.randomUUID().toString();
        JSONObject json = JSON.parseObject(dataJson);

        json.put("ID", id);
        json.put("FormID", formID);
        mongoTemplate.save(json, MongoCollectionNames.FORM_DATA);
        return id;
    }
}
