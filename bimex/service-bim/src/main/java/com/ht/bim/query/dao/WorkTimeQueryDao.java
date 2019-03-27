package com.ht.bim.query.dao;

import com.ht.bim.command.dto.WorkTimeDto;
import com.ht.bim.query.vo.WorkTimeVo;

import java.util.List;

/**
 * Created by dlj on 2019-3-18.
 */
public interface WorkTimeQueryDao {

    List<WorkTimeVo> queryWorkTime(WorkTimeDto dto);

    WorkTimeVo queryWorkTimeByID(String iD);
}
