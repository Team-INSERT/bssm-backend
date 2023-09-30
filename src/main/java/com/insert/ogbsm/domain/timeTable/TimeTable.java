package com.insert.ogbsm.domain.timeTable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeTable {

    @EmbeddedId
    private TimeTablePk pk;

    private String subject;

    public TimeTable(LocalDate date, int grade, int classNumber, Period period, String subject) {
        this.pk = new TimeTablePk(date, grade, classNumber, period);
        this.subject = subject;
    }

}
