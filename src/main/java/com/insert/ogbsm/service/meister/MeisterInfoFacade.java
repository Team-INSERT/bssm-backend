package com.insert.ogbsm.service.meister;

import com.insert.ogbsm.domain.meister.MeisterInfo;
import com.insert.ogbsm.domain.meister.repository.MeisterInfoRepository;
import com.insert.ogbsm.domain.user.Student;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.StudentRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeisterInfoFacade {

    private final MeisterInfoRepository meisterInfoRepository;
    private final StudentRepo studentRepo;

    public MeisterInfo getMeisterInfo(User user) {
        return meisterInfoRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new BsmException(ErrorCode.MEISTER_INFO_NOT_FOUND));
    }

    public Student getStudent(MeisterInfo meisterInfo) {
        return studentRepo.findByEmail(meisterInfo.getEmail())
                .orElse(null);
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
}
