package com.insert.ogbsm.presentation.calender.dto;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.Date;
import com.insert.ogbsm.domain.calender.Type;

public record CalenderReq(Long id, String title, int priority, Date date, Long color, Long userId, Type type,
                          Short grade, Short classNumber) {
    public Calender toEntity() {
        Calender calender = new Calender(title, priority, date, color, type, grade, classNumber);
        calender.updateUserId(userId);

        return calender;
    }
}
