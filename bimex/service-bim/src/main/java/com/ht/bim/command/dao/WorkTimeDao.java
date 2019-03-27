package com.ht.bim.command.dao;

import com.ht.bim.command.dto.WorkTimeDto;

import java.util.List;

/**
 * Created by dlj on 2019-3-19.
 */
public interface WorkTimeDao {
    void addWorkTime(WorkTimeDto dto);

    void updateWorkTime(WorkTimeDto dto);

    void deleteWorkTime(String iD);

    void updateStatus(WorkTimeDto dto);

    void updateStatusAndRR(WorkTimeDto workTimeDto);

    void updateStatusandWR(WorkTimeDto workTimeDto);
}
