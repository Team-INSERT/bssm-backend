package com.insert.ogbsm.presentation.calender.dto;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.Date;
import com.insert.ogbsm.domain.calender.Type;

public record CalenderReq(String title, int priority, Date date, String color, Type type,
                          Short grade, Short classNumber) {

    public Calender toEntity(Long id) {
        Calender calender = new Calender(title, priority, date, color, type, grade, classNumber);
        calender.updateUserId(id);

        return calender;
    }
}
