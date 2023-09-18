package com.insert.ogbsm.domain.calender.repo;


import com.insert.ogbsm.presentation.calender.dto.CalenderRes;

import java.util.List;

public interface CalenderDao {
    List<CalenderRes> findBySchool(Integer month);

    List<CalenderRes> findByGrade(Integer month, Short grade);

    List<CalenderRes> findByClass(Integer month, Short grade, Short classNumber);
}
