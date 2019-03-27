package com.ht.bim.command.service;

import com.ht.bim.command.dao.SysFormFieldDao;
import com.ht.bim.command.dto.SysFormFieldDto;
import com.ht.bim.command.po.SysFormFieldPo;
import ht.framework.exception.ValidateException;
import ht.framework.util.Validator;
import ht.service.common.service.SerialNoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhong on 2019-3-17.
 */
@Service
public class SysFormFieldService {

    @Resource
    private SysFormFieldDao sysFormFieldDao;
    @Resource
    private SerialNoService serialNoService;

    @Transactional(rollbackFor = Throwable.class)
    public void createFormField(String formId, List<SysFormFieldDto> fields) {
        validate(formId, fields);
        for (SysFormFieldDto param : fields) {
            create(formId, param);
        }
    }

    private void create(String formId, SysFormFieldDto param) {
        SysFormFieldPo po = new SysFormFieldPo();
        po.setFormFieldID(serialNoService.newId());
        po.setFormID(formId);
        po.setFieldName(param.getFieldName());
        po.setFieldType(param.getFieldType());
        po.setDataFuncName(param.getDataFuncName());
        po.setFieldCaption(param.getFieldCaption());
        po.setDataOrder(param.getDataOrder());
        sysFormFieldDao.create(po);
    }

    private void validate(String formId, List<SysFormFieldDto> fields) {
        Validator.notBlank(formId, "表单编号不能为空");
        if (CollectionUtils.isEmpty(fields)) {
            throw new ValidateException("表单字段不能为空");
        }
        //
    }
}
