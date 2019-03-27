package ht.service.common.service;

import ht.framework.util.TimeSource;
import ht.framework.util.Util;
import ht.service.common.dao.SerialNoDao;
import ht.service.common.po.SerialNoPo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by zhong on 2017/11/28.
 */
@Service
public class SerialNoService {

    @Resource
    private SerialNoDao serialNoDao;
    @Resource
    private TimeSource timeSource;

    @Transactional(rollbackFor = {Throwable.class}, propagation = Propagation.REQUIRES_NEW)
    public String newSerialNo(String name) {
        serialNoDao.lockRecord(name);
        SerialNoPo po = serialNoDao.find(name);
        if (po == null) {
            po = createSerialNo(name);
        }
        int newValue = po.getCurrentValue() + 1;
        serialNoDao.updateNewValue(name, po.getCurrentValue(), newValue);
        return buildSerialNo(po, newValue);
    }

    private SerialNoPo createSerialNo(String name) {
        SerialNoPo po = new SerialNoPo();
        po.setSerialName(name);
        po.setVer(0);
        po.setCurrentValue(10000);
        po.setValueLength(5);
        serialNoDao.create(po);
        return po;
    }

    private String buildSerialNo(SerialNoPo po, int newValue) {
        String flexPart;
        Integer valueLength = po.getValueLength();
        if (Util.nvl(valueLength, 0) > 0) {
            flexPart = StringUtils.leftPad(new Integer(newValue).toString(), valueLength, '0');
        } else {
            flexPart = new Integer(newValue).toString();
        }
        String timeStamp = DateFormatUtils.format(timeSource.getCurrentDate(), "yyyyMMdd");
        StringBuilder sb = new StringBuilder(128);
        sb.append(Util.nvl(po.getPrefix(), ""));
        sb.append(timeStamp);
        sb.append(flexPart);
        sb.append(Util.nvl(po.getSuffix(), ""));
        return sb.toString();
    }

    public String newId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
