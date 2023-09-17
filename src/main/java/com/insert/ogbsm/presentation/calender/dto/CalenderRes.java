package com.insert.ogbsm.presentation.calender.dto;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.Date;
import com.insert.ogbsm.domain.calender.Type;
import lombok.Getter;

@Getter
public class CalenderRes {
    final Long id;
    final String title;
    final int priority;
    final Date date;
    final Long color;
    final Type type;
    final Long userId;

    public CalenderRes(Calender calender) {
        this.id = calender.getId();
        this.title = calender.getTitle();
        this.priority = calender.getPriority();
        this.date = calender.getDate();
        this.color = calender.getColor();
        this.type = calender.getType();
        this.userId = calender.getUserId();
    }
}
