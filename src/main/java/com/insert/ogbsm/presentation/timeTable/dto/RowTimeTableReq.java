package com.insert.ogbsm.presentation.timeTable.dto;

import com.insert.ogbsm.domain.timeTable.Period;
import com.insert.ogbsm.domain.timeTable.TimeTable;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public final class RowTimeTableReq {
    private String GRADE;
    private String CLASS_NM;
    private String PERIO;
    private String ITRT_CNTNT;
    private String ALL_TI_YMD;


    public TimeTable toEntity() {
        return new TimeTable(
                LocalDate.parse(ALL_TI_YMD, DateTimeFormatter.ofPattern("yyyyMMdd")),
                Integer.parseInt(GRADE),
                Integer.parseInt(CLASS_NM),
                Period.get(Integer.parseInt(PERIO)),
                ITRT_CNTNT
        );
    }
}
