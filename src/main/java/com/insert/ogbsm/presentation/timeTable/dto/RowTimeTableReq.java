package com.insert.ogbsm.presentation.timeTable.dto;

import com.insert.ogbsm.domain.timeTable.Period;
import com.insert.ogbsm.domain.timeTable.TimeTable;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public final class RowTimeTableReq {
    private Integer GRADE;
    private Integer CLASS_NM;
    private Integer PERIO;
    private String ITRT_CNTNT;
    private String ALL_TI_YMD;


    public TimeTable toEntity() {
        return new TimeTable(
                LocalDate.parse(ALL_TI_YMD, DateTimeFormatter.ofPattern("yyyyMMdd")),
                GRADE,
                CLASS_NM,
                Period.get(PERIO),
                ITRT_CNTNT
        );
    }
}
