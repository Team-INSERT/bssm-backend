package com.insert.ogbsm.service.timeTable.business;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.timeTable.dto.TimeTableRes;
import com.insert.ogbsm.service.timeTable.implement.TimeTableImplements;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeTableBusiness {
    private final TimeTableImplements timeTableImplements;

    public TimeTableRes getBarByGradeAndClass(User user) {
        return timeTableImplements.readBarByUserGradeAndClassNumber(user);
    }

    public TimeTableRes getTableByGradeAndClass(User user) {
        return timeTableImplements.readTableByUserGradeAndClassNumber(user);
    }
}
