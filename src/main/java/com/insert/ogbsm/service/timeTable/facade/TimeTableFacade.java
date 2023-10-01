package com.insert.ogbsm.service.timeTable.facade;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;

@Component
public class TimeTableFacade {
    public List<LocalDate> getStartAndEndOfWeek() {
        //오늘
        LocalDate today = LocalDate.now();

        int day = today.get(DAY_OF_WEEK);
        if (day == 7) {
            day = 0;
        }
        LocalDate start = today.minusDays(day);
        LocalDate end = start.plusDays(6);

        return List.of(start, end);
    }
}
