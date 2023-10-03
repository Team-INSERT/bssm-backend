package com.insert.ogbsm.service.timeTable;

import com.insert.ogbsm.domain.timeTable.repo.TimeTableRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.timeTable.dto.TimeTableRes;
import com.insert.ogbsm.service.timeTable.facade.TimeTableFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeTableReadService {
    private final TimeTableRepo timeTableRepo;
    private final TimeTableFacade timetableFacade;

    public TimeTableRes getBarByGradeAndClass(User user) {

        GradeAndClassNumber gradeAndClass = getGradeAndClass(user);
        List<LocalDate> startAndEndOfWeek = timetableFacade.getStartAndEndOfWeek();

        return new TimeTableRes(
                timeTableRepo.findByGradeAndClassNumber(gradeAndClass.grade(), gradeAndClass.classNumber(), startAndEndOfWeek)
        );
    }

    public TimeTableRes getTableByGradeAndClass(User user) {

        GradeAndClassNumber gradeAndClass = getGradeAndClass(user);
        List<LocalDate> startAndEndOfWeek = timetableFacade.getStartAndEndOfWeek();

        return new TimeTableRes(
                timeTableRepo.findByGradeAndClassNumber(gradeAndClass.grade(), gradeAndClass.classNumber(), startAndEndOfWeek),
                "table"
        );
    }


    private GradeAndClassNumber getGradeAndClass(User user) {
        if (user == null) {
            return new GradeAndClassNumber(1, 1);
        }

        return new GradeAndClassNumber(user.getGrade(), user.getClass_number());
    }

    private record GradeAndClassNumber(int grade, int classNumber) {
    }
}
