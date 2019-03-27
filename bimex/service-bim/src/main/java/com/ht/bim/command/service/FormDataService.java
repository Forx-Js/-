package com.ht.bim.command.service;

import com.ht.bim.command.dao.ProcessFormConfigDao;
import com.ht.bim.command.dao.ProcessRuntimeFormDataDao;
import com.ht.bim.command.dto.form.FormDataDto;
import com.ht.bim.command.po.ProcessFormConfigPo;
import com.ht.bim.command.po.ProcessRuntimeFormDataPo;
import ht.framework.util.TimeSource;
import ht.framework.util.Validator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by zhong on 2019-3-17.
 */
@Service
public class FormDataService {

    @Resource
    private ProcessRuntimeFormDataDao processRuntimeFormDataDao;
    @Resource
    private TimeSource timeSource;
    @Resource
    private ProcessFormConfigDao processFormConfigDao;

    public String saveFormData(FormDataDto dto, String userId) {
        validate(dto);
        ProcessRuntimeFormDataPo po = processRuntimeFormDataDao.findByKeyFields(dto.getFormConfigID(),
                dto.getRuntimeID());
        if (po == null) {
            po = createDataPo(dto, userId);
            processRuntimeFormDataDao.create(po);
        } else {
            refreshDataPo(po, dto, userId);
            processRuntimeFormDataDao.update(po);
        }
        return po.getRuntimeFormDataID();
    }

    private ProcessRuntimeFormDataPo createDataPo(FormDataDto dto, String userId) {
        ProcessRuntimeFormDataPo po = new ProcessRuntimeFormDataPo();
        po.setRuntimeFormDataID(UUID.randomUUID().toString());
        po.setFormConfigID(dto.getFormConfigID());
        po.setRunTimeID(dto.getRuntimeID());
        po.setCreateUserID(userId);
        po.setCreateTime(timeSource.getCurrentTime());
        po.setDataJson(dto.getDataJson());
        ProcessFormConfigPo configPo = processFormConfigDao.find(dto.getFormConfigID());
        po.setProcessID(configPo.getProcessID());
        po.setNodeID(configPo.getNodeID());
        return po;
    }

    private void validate(FormDataDto dto) {
        dto.validate();
        ProcessFormConfigPo configPo = processFormConfigDao.find(dto.getFormConfigID());
        Validator.notNull(configPo, "找不到对应的配置项");
    }


    private void refreshDataPo(ProcessRuntimeFormDataPo po, FormDataDto dto, String userId) {
        po.setDataJson(dto.getDataJson());
        po.setUpdateTime(timeSource.getCurrentTime());
        po.setUpdateUserID(userId);
    }
}
