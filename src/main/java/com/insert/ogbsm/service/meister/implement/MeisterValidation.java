package com.insert.ogbsm.service.meister.implement;

import com.insert.ogbsm.domain.meister.MeisterInfo;
import com.insert.ogbsm.domain.meister.repository.MeisterInfoRepository;
import com.insert.ogbsm.domain.user.Student;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MeisterValidation {

    private final MeisterInfoRepository meisterInfoRepository;

    public void mustBeAbleToUpdate(MeisterInfo meisterInfo) {
        LocalDateTime availableTime = meisterInfo.getLastPrivateDate().plusDays(1);

        if (LocalDateTime.now().isBefore(availableTime)) {
            throw new BsmException(ErrorCode.MEISTER_CANNOT_CHANGE_AUTH);
        }
    }

    public void viewPermissionCheckRank(User user) {
        MeisterInfo info = meisterInfoRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new BsmException(ErrorCode.MEISTER_INFO_NOT_FOUND));
        if (info.isLoginError()) {
            throw new BsmException(ErrorCode.MEISTER_PASSWORD_CHANGED);
        }
        if (info.isPrivateRanking()) {
            throw new BsmException(ErrorCode.MEISTER_INFO_PRIVATE);
        }
    }

    public void viewPermissionCheck(User user, Student student) {
        MeisterInfo info = meisterInfoRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new BsmException(ErrorCode.MEISTER_INFO_NOT_FOUND));
        if (info.isLoginError()) {
            throw new BsmException(ErrorCode.MEISTER_PASSWORD_CHANGED);
        }
        if (info.isPrivateRanking() && !user.getEmail().equals(student.getEmail())) {
            throw new BsmException(ErrorCode.MEISTER_INFO_PRIVATE);
        }
    }
}
