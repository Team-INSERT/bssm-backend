package com.insert.ogbsm.service.calender;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.repo.CalenderRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.calender.dto.CalenderReq;
import com.insert.ogbsm.presentation.calender.dto.CalenderRes;
import com.insert.ogbsm.service.validation.CalenderValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalenderDefService {
    private final CalenderRepo calenderRepo;
    private final CalenderValidation calenderValidation;

    public CalenderRes create(CalenderReq calenderReq, User user) {
        Calender calender = calenderReq.toEntity();

        calenderValidation.checkHasAuthToDef(calender, user);

        calender.updateUserId(user.getId());
        Calender save = calenderRepo.save(calender);

        return new CalenderRes(save);
    }

    public CalenderRes update(CalenderReq calenderReq, User user) {
        Calender calender = calenderReq.toEntity();
        calenderValidation.checkHasAuthToDef(calender, user);
        calenderRepo.save(calender);

        return new CalenderRes(calender);
    }

    public void delete(Long calenderId, User user) {
        Calender calender = calenderRepo.findById(calenderId)
                .orElseThrow(() -> new BsmException(ErrorCode.CALENDER_NOT_FOUND));

        calenderValidation.checkHasAuthToDef(calender, user);

        calenderRepo.delete(calender);
    }
}
