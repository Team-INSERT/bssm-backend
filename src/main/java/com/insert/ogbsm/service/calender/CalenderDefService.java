package com.insert.ogbsm.service.calender;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.repo.CalenderRepo;
import com.insert.ogbsm.domain.calender.repo.CalenderWrapper;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.calender.dto.CalenderReq;
import com.insert.ogbsm.presentation.calender.dto.CalenderRes;
import com.insert.ogbsm.service.validation.CalenderValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalenderDefService {
    private final CalenderRepo calenderRepo;
    private final CalenderWrapper calenderWrapper;
    private final CalenderValidation calenderValidation;

    public CalenderRes create(CalenderReq calenderReq, User user) {
        Calender calender = calenderReq.toEntity();

        calenderValidation.checkHasAuthToDef(calender, user);

        calender.updateUserId(user.getId());
        Calender save = calenderRepo.save(calender);

        return new CalenderRes(save, user);
    }

    public CalenderRes update(CalenderReq calenderReq, User user) {
        Calender calender = calenderReq.toEntity();
        calenderValidation.checkHasAuthToDef(calender, user);
        calenderRepo.save(calender);

        return new CalenderRes(calender, user);
    }

    public void delete(Long calenderId, User user) {
        Calender calender = calenderWrapper.findById(calenderId);

        calenderValidation.checkHasAuthToDef(calender, user);

        calenderRepo.delete(calender);
    }

    public void updatePriority(Long calenderId, int priority, User user) {
        Calender calender = calenderWrapper.findById(calenderId);

        calenderValidation.checkHasAuthToDef(calender, user);

        calender.updatePriority(priority);
    }
}
