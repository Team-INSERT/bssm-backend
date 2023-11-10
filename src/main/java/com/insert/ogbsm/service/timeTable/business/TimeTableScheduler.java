package com.insert.ogbsm.service.timeTable.business;

import com.insert.ogbsm.domain.timeTable.TimeTable;
import com.insert.ogbsm.domain.timeTable.repo.TimeTableRepo;
import com.insert.ogbsm.service.timeTable.implement.TimeTableImplements;
import com.insert.ogbsm.service.timeTable.implement.TimeTableProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TimeTableScheduler {
    private final TimeTableProvider timeTableProvider;
    private final TimeTableRepo timeTableRepo;
    private final TimeTableImplements timeTableImplements;

    @Scheduled(cron = "0 0 12 ? * SUN")
    private void getMonthTimeTable() throws IOException {
        List<LocalDate> startAndEndOfWeek = timeTableImplements.getStartAndEndOfWeek();
        List<TimeTable> timeTableList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            timeTableList.addAll(timeTableProvider.getRowWeekTimeTableList(startAndEndOfWeek.get(0).plusDays(i)));
        }
        timeTableRepo.saveAll(timeTableList);
    }
}
