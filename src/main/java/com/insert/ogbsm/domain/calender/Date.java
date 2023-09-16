package com.insert.ogbsm.domain.calender;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Date {
    private int month;
    private int day;

    public Date(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public Date() {

    }
}
