package com.insert.ogbsm.domain.calender;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Date {
    private int month;
    private int day;

    public Date(int month, int day) {
        this.month = month;
        this.day = day;
    }
}
