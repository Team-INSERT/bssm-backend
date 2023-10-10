package com.insert.ogbsm.presentation.calender.dto;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.Type;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Getter
public class CalenderReq {

    @NotNull
    private String title;

    @NotNull
    private int priority;

    @NotNull
    private String date;

    @NotNull
    private String color;

    @NotNull
    private Type type;

    @NotNull
    private Short grade;

    @NotNull
    private Short classNumber;

    public Calender toEntity(Long id) {
        Calender calender = null;
        try {
            calender = new Calender(title, priority, LocalDate.parse(date), color, type, grade, classNumber);
        } catch (DateTimeParseException e) {
            throw new BsmException(ErrorCode.Invalid_Date);
        }

        calender.updateUserId(id);

        return calender;
    }
}
