package com.insert.ogbsm.presentation.calender.dto;

import java.util.List;

public record CalenderGraphRes(List<CalenderRes> findByClass, List<CalenderRes> findByGrade,
                               List<CalenderRes> findBySchool) {
}
