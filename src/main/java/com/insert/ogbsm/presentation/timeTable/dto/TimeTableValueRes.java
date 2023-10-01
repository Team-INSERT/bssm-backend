package com.insert.ogbsm.presentation.timeTable.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insert.ogbsm.domain.timeTable.Period;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public final class TimeTableValueRes {
    private final Period period;
    private final String subject;
    @JsonIgnore
    private final LocalDate date;
    private final Period.Time startTime;
    private final Period.Time endTime;


    @QueryProjection
    public TimeTableValueRes(Period period, String subject, LocalDate date) {
        this.period = period;
        this.subject = subject;
        this.date = date;
        this.startTime = period.getStartTime();
        this.endTime = period.getEndTime();
    }
    
}
