package com.insert.ogbsm.service.timeTable;

import com.insert.ogbsm.domain.timeTable.TimeTable;
import com.insert.ogbsm.domain.timeTable.repo.TimeTableRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTableAdminService {
    private final TimeTableRepo timeTableRepo;
    private final TimeTableProvider timeTableProvider;

    public void setMonthTimeTable(LocalDate localDate) throws IOException {
        List<TimeTable> rowWeekTimeTableList = timeTableProvider.getRowWeekTimeTableList(localDate);

        timeTableRepo.saveAll(rowWeekTimeTableList);
    }
}
