package com.insert.ogbsm.service.calender.implement;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.Type;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.insert.ogbsm.domain.calender.Type.CLASS;
import static com.insert.ogbsm.domain.calender.Type.GRADE;

@Component
public class CalenderValidation {

    private void ifFalseThrowNoAuthException(boolean boll) {
        if(boll) throw new BsmException(ErrorCode.NO_AUTH_TO_DEF_CALENDER);
    }

    public void checkHasAuthToDef(Calender calender, User user) {
        switch (calender.getType()){
            case GRADE -> checkSameGrade(calender, user);
            case CLASS -> checkSameClass(calender, user);
        }
    }

    private void checkSameClass(Calender calender, User user) {
        checkSameGrade(calender,user);
        ifFalseThrowNoAuthException(!calender.getClassNumber().equals(user.getClass_number()));
    }

    private void checkSameGrade(Calender calender, User user) {
        ifFalseThrowNoAuthException(!calender.getGrade().equals(user.getGrade()));
    }
}
