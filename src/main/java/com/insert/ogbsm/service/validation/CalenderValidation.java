package com.insert.ogbsm.service.validation;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.Type;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CalenderValidation {

    public void checkHasAuthToDef(Calender calender, User user) {
        Type calenderType = calender.getType();
        if (calenderType == Type.SCHOOL) {
        } else if (calenderType == Type.GRADE) {
            checkSameGrade(calender.getGrade(), user.getGrade());
        } else if (calenderType == Type.CLASS) {
            checkSameClass(calender.getGrade(), calender.getClassNumber(), user.getGrade(), user.getClass_number());
        }
    }

    private void checkSameClass(Short calGrade, Short calClassNumber, Short userGrade, Short userClassNumber) {
        if (!Objects.equals(calGrade, userGrade) || !Objects.equals(calClassNumber, userClassNumber)) {
            throw new BsmException(ErrorCode.NO_AUTH_TO_DEF_CALENDER);
        }
    }

    private void checkSameGrade(Short calenderGrade, Short userGrade) {
        if (!Objects.equals(calenderGrade, userGrade)) {
            throw new BsmException(ErrorCode.NO_AUTH_TO_DEF_CALENDER);
        }
    }
}
