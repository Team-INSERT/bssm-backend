package com.insert.ogbsm.presentation.calender.dto;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.Date;
import com.insert.ogbsm.domain.calender.Type;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.user.dto.UserSimpleRes;
import lombok.Getter;

@Getter
public class CalenderRes {
    final Long id;
    final String title;
    final int priority;
    final Date date;
    final Long color;
    final Type types;
    final UserSimpleRes userSimpleRes;

    public CalenderRes(Calender calender, User user) {
        this.id = calender.getId();
        this.title = calender.getTitle();
        this.priority = calender.getPriority();
        this.date = calender.getDate();
        this.color = calender.getColor();
        this.types = calender.getType();
        this.userSimpleRes = new UserSimpleRes(user);
    }
}
