package com.insert.ogbsm.domain.timeTable.repo;

import com.insert.ogbsm.presentation.timeTable.dto.TimeTableValueRes;

import java.time.LocalDate;
import java.util.List;

public interface TimeTableDao {
    List<TimeTableValueRes> findByGradeAndClassNumber(int grade, int classNumber, List<LocalDate> startAndEndOfWeek);
}
