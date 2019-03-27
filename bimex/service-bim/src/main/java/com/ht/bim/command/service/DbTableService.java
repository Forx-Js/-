package com.ht.bim.command.service;

import com.ht.bim.command.dto.db.ColumnInfo;
import com.ht.bim.command.dto.db.TableInfo;
import com.ht.bim.command.dto.form.FormItem;
import com.ht.bim.command.dto.form.FormModel;
import com.ht.bim.command.po.ProcessFormConfigPo;
import ht.framework.exception.ValidateException;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhong on 2019-2-5.
 */
@Service
public class DbTableService {

    public void createTable(ProcessFormConfigPo tableName, FormModel model) {
        TableInfo table = new TableInfo();
        createColumns(model.getList());

        // TODO 完善代码
        throw new NotImplementedException();
    }

    private void createColumns(List<FormItem> items) {
        List<ColumnInfo> columns = new ArrayList<>();
        for (FormItem item : items) {
            validateFormItem(item);
            ColumnInfo column = new ColumnInfo();
            column.setColumnName(item.getName());
            column.setConfigKey(item.getKey());
            column.setDataType(item.getType());
            columns.add(column);
        }
    }

    private void validateFormItem(FormItem item) {
        item.validate();
        final String IDENTITY_PATTERN = "^[A-Za-z_][A-Za-z_]*$";
        if (!item.getModel().matches(IDENTITY_PATTERN)) {
            throw new ValidateException("表单项字段名称格式不正确");
        }
    }
}
