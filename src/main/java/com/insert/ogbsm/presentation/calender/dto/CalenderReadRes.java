package com.insert.ogbsm.presentation.calender.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CalenderReadRes {
    private LocalDate date;
    private List<CalenderRes> plans;
}
