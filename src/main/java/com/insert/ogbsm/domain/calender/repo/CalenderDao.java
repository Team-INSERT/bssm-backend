package com.insert.ogbsm.domain.calender.repo;


import com.insert.ogbsm.presentation.calender.dto.CalenderRes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CalenderDao {
    Map<LocalDate, List<CalenderRes>> findMonthCalender(Integer year, Integer month, Short grade, Short classNumber);
}
