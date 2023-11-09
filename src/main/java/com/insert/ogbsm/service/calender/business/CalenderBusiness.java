package com.insert.ogbsm.service.calender.business;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.calender.dto.CalenderReadRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderSimpleRes;
import com.insert.ogbsm.service.calender.implement.CalenderImplement;
import com.insert.ogbsm.service.validation.CalenderValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.insert.ogbsm.service.calender.implement.CalenderImplement.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CalenderBusiness {
    private final CalenderImplement calenderImplement;
    private final CalenderValidation calenderValidation;

    public CalenderRes append(Calender calender, User user) {
        calenderValidation.checkHasAuthToDef(calender, user);
        Calender save = calenderImplement.append(calender);

        return new CalenderRes(save, user);
    }

    public void remove(Long calenderId, User user) {
        Calender calender = calenderImplement.read(calenderId);
        calenderValidation.checkHasAuthToDef(calender, user);
        calenderImplement.remove(calender);
    }

    public void updatePriority(Long calenderId, int priority, User user) {
        Calender calender = calenderImplement.read(calenderId);
        calenderValidation.checkHasAuthToDef(calender, user);

        calender.updatePriority(priority);
    }

    @Transactional(readOnly = true)
    public List<CalenderReadRes> read(int year, int month, Optional<User> user) {
        StartAndEndDate startAndEndDate = calenderImplement.getStartAndEndDay(year, month);
        return calenderImplement.readAll(year, month, user, startAndEndDate);
    }

    @Transactional(readOnly = true)
    public CalenderSimpleRes readByDate(LocalDate date) {
        return calenderImplement.readByDate(date);
    }
}
