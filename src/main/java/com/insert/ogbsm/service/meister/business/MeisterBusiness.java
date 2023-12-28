package com.insert.ogbsm.service.meister.business;

import com.insert.ogbsm.domain.meister.MeisterData;
import com.insert.ogbsm.domain.meister.MeisterInfo;
import com.insert.ogbsm.domain.user.Student;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.meister.dto.request.MeisterDetailRequest;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterDetailResponse;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResponse;
import com.insert.ogbsm.service.meister.implement.*;
import com.insert.ogbsm.service.user.implement.StudentImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MeisterBusiness {

    private final MeisterImplement meisterImplement;
    private final StudentImplement studentImplement;
    private final MeisterValidation meisterValidation;

    public MeisterDetailResponse getDetail(User user, MeisterDetailRequest dto) throws IOException {
        Student student = studentImplement.readByGradeClassNumber(dto.getGrade(), dto.getClassNo(), dto.getStudentNo());

        meisterValidation.viewPermissionCheck(user, student);
        MeisterData meisterData = meisterImplement.readMiesterData(student);
        MeisterInfo meisterInfo = meisterData.getMeisterInfo();
        meisterInfo.privateCheck(user.getEmail());

        meisterImplement.tryLogin(student, meisterInfo, dto.getPw());
        return meisterImplement.updateMeisterDetail(student, meisterData);
    }

    public MeisterResAndAvgAndMax get(User user) {
        MeisterData meisterData = meisterImplement.readMeisterData(user);
        
        MeisterInfo meisterInfo = meisterData.getMeisterInfo();
        if (meisterInfo.isLoginError()) {
            return meisterImplement.getErrorStudentResWithAvg(user, meisterInfo);
        }
        return meisterImplement.getNormalStudentResWithAvg(user, meisterData);
    }

    public MeisterResponse updateAndGet(User user) {
        MeisterData meisterData = meisterImplement.getNewMeisterData(user);
        MeisterInfo meisterInfo = meisterData.getMeisterInfo();

        if (meisterInfo.isLoginError()) {
            return meisterImplement.getErrorStudentRes(meisterInfo);
        }

        return meisterImplement.getNormalStudentRes(meisterData);
    }
}
