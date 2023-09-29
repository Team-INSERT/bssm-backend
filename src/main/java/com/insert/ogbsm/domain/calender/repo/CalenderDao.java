package com.insert.ogbsm.domain.calender.repo;


import com.insert.ogbsm.presentation.calender.dto.CalenderGraphRes;

public interface CalenderDao {
    CalenderGraphRes get(Integer year, Integer month, Short grade, Short classNumber);
}
