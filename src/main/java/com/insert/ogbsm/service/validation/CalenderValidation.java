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

    private static void ifFalseThrowNoAuthException(boolean boll) {
        if (boll) {
            throw new BsmException(ErrorCode.NO_AUTH_TO_DEF_CALENDER);
        }
    }

    public void checkHasAuthToDef(Calender calender, User user) {
        Type calenderType = calender.getType();
        if (calenderType == Type.SCHOOL) {
            return;
        } else if (calenderType == Type.GRADE) {
            checkSameGrade(calender.getGrade(), user.getGrade());
        } else if (calenderType == Type.CLASS) {
            checkSameClass(calender.getGrade(), calender.getClassNumber(), user.getGrade(), user.getClass_number());
        }
    }

    private void checkSameClass(Short calGrade, Short calClassNumber, Short userGrade, Short userClassNumber) {
        ifFalseThrowNoAuthException(!Objects.equals(calGrade, userGrade) || !Objects.equals(calClassNumber, userClassNumber));
    }

    private void checkSameGrade(Short calenderGrade, Short userGrade) {
        ifFalseThrowNoAuthException(!Objects.equals(calenderGrade, userGrade));
    }
}
