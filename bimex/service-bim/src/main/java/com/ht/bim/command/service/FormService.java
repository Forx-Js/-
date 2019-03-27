package com.ht.bim.command.service;

import com.ht.bim.command.dao.ProcessFormConfigDao;
import com.ht.bim.command.dto.form.CreateFormDto;
import com.ht.bim.command.po.ProcessFormConfigPo;
import com.ht.bim.command.po.SysFormJsonPo;
import com.ht.bim.repository.FormRepository;
import ht.framework.exception.ValidateException;
import ht.framework.util.TimeSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * 表单设计器和表单实现的服务
 *
 * @author zhong
 * @date 2019-2-5
 */
@Service
public class FormService {

    @Resource
    private TimeSource timeSource;
    @Resource
    private ProcessFormConfigDao processFormConfigDao;
    @Autowired
    private FormRepository formRepository;

    @Transactional(rollbackFor = Throwable.class)
    public String createForm(CreateFormDto dto, String user) throws IOException {
        dto.validate();
        validateIfExistForm(dto);
        ProcessFormConfigPo po = createFormConfig(dto, user);
        saveJson(po.getFormConfigID(), dto.getJson());
        saveFormFields(po.getFormConfigID(), dto);
        return po.getFormConfigID();
    }

    private void validateIfExistForm(CreateFormDto dto) {
        String nodeId = dto.getNodeId();
        if (StringUtils.isBlank(nodeId)) {
            nodeId = dto.getProcessId();
        }
        ProcessFormConfigPo po = processFormConfigDao.findByKeyField(dto.getProcessId(), nodeId);
        if (po != null) {
            throw new ValidateException("节点的表单已经存在，请不要重复创建");
        }
    }

    private ProcessFormConfigPo createFormConfig(CreateFormDto dto, String user) {
        ProcessFormConfigPo po = new ProcessFormConfigPo();
        po.setFormConfigID(UUID.randomUUID().toString());
        po.setProcessID(dto.getProcessId());
        po.setNodeID(StringUtils.isBlank(dto.getNodeId()) ? dto.getProcessId() : dto.getNodeId());
        po.setCreateUserID(user);
        po.setCreateTime(timeSource.getCurrentTime());
        po.setDeleted(false);
        po.validate();
        processFormConfigDao.create(po);
        return po;
    }

    private void saveJson(String formConfigID, String json) {
        SysFormJsonPo po = new SysFormJsonPo();
        po.setFormJsonID(UUID.randomUUID().toString());
        po.setFormID(formConfigID);
        po.setFormJson(json);
        po.setCreateTime(timeSource.getCurrentTime());
        formRepository.saveJson(po);
    }

    private void saveFormFields(String formConfigID, CreateFormDto dto) throws IOException {
        // TODO 从 json 中解析出字段，存储到表中
    }

}
