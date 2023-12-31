package com.insert.ogbsm.presentation.calender.dto;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.Type;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.user.dto.UserSimpleRes;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CalenderRes {
    final Long id;
    final String title;
    final int priority;
    final LocalDate date;
    final String color;
    final Type type;
    final Short grade;
    final Short classNumber;
    final UserSimpleRes user;

    public CalenderRes(Calender calender, User user) {
        this.id = calender.getId();
        this.title = calender.getTitle();
        this.priority = calender.getPriority();
        this.date = calender.getDate();
        this.color = calender.getColor();
        this.type = calender.getType();
        this.grade = calender.getGrade();
        this.classNumber = calender.getClassNumber();
        this.user = new UserSimpleRes(user);
    }
}
