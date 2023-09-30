package com.insert.ogbsm.domain.timeTable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
@NoArgsConstructor
public class TimeTablePk implements Serializable {

    @EqualsAndHashCode.Include
    private LocalDate date;

    @EqualsAndHashCode.Include
    private int grade;

    @EqualsAndHashCode.Include
    private int classNumber;

    @EqualsAndHashCode.Include
    private Period period;

    public TimeTablePk(LocalDate date, int grade, int classNumber, Period period) {
        this.date = date;
        this.grade = grade;
        this.classNumber = classNumber;
        this.period = period;
    }
}
