package com.insert.ogbsm.domain.room;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.time.temporal.IsoFields;

@Embeddable
public class YearSemester {
    private int year;
    @Min(0)
    @Max(3)
    private int semester;

    public YearSemester() {
        LocalDate localDate = LocalDate.now();
        this.year = localDate.getYear();
        this.semester = (localDate.get(IsoFields.QUARTER_OF_YEAR)) <= 2 ? 1 : 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YearSemester that = (YearSemester) o;

        if (year != that.year) return false;
        return semester == that.semester;
    }

}
