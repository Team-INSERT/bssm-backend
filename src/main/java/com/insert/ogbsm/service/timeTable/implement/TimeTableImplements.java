package com.insert.ogbsm.service.timeTable.implement;

import com.insert.ogbsm.domain.timeTable.repo.TimeTableRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.timeTable.dto.TimeTableRes;
import com.insert.ogbsm.presentation.timeTable.dto.TimeTableValueRes;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;

@Component
public class TimeTableImplements {

    private final TimeTableRepo timeTableRepo;

    public TimeTableImplements(TimeTableRepo timeTableRepo) {
        this.timeTableRepo = timeTableRepo;
    }

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

    public GradeAndClassNumber getGradeAndClass(User user) {
        if (user == null) {
            return new GradeAndClassNumber(1, 1);
        }

        return new GradeAndClassNumber(user.getGrade(), user.getClass_number());
    }

    public TimeTableRes readBarByUserGradeAndClassNumber(User user) {
        List<TimeTableValueRes> timeTable = getTimeTableValueRes(user);
        return new TimeTableRes(timeTable);
    }

    public TimeTableRes readTableByUserGradeAndClassNumber(User user) {
        List<TimeTableValueRes> timeTable = getTimeTableValueRes(user);
        return new TimeTableRes(timeTable, "table");
    }

    private List<TimeTableValueRes> getTimeTableValueRes(User user) {
        GradeAndClassNumber gradeAndClass = getGradeAndClass(user);
        List<LocalDate> startAndEndOfWeek = getStartAndEndOfWeek();
        return timeTableRepo.findByGradeAndClassNumber(gradeAndClass.grade(), gradeAndClass.classNumber(), startAndEndOfWeek);
    }

    public record GradeAndClassNumber(int grade, int classNumber) {
    }
}
