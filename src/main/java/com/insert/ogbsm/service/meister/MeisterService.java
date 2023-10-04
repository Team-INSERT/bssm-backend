package com.insert.ogbsm.service.meister;

import com.insert.ogbsm.domain.meister.MeisterData;
import com.insert.ogbsm.domain.meister.MeisterInfo;
import com.insert.ogbsm.domain.meister.repository.MeisterDataRepository;
import com.insert.ogbsm.domain.meister.repository.MeisterInfoRepository;
import com.insert.ogbsm.domain.user.Student;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.StudentRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.meister.dto.request.MeisterDetailRequest;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterDetailResponse;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvg;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MeisterService {

    private final StudentRepo studentRepo;
    private final MeisterInfoRepository meisterInfoRepository;
    private final MeisterDataRepository meisterDataRepository;
    private final MeisterParsingProvider meisterProvider;
    private final MeisterAuthProvider meisterAuthProvider;
    private final MeisterDataProvider meisterDataProvider;
    private final MeisterInfoFacade meisterInfoFacade;

    public MeisterDetailResponse getDetail(User user, MeisterDetailRequest dto) throws IOException {
        Student student = studentRepo.findByGradeAndClassNoAndStudentNo(dto.getGrade(), dto.getClassNo(), dto.getStudentNo())
                .orElseThrow(() -> new BsmException(ErrorCode.STUDENT_NOT_FOUND));
        meisterInfoFacade.viewPermissionCheck(user, student);

        MeisterData meisterData = meisterDataProvider.findOrElseCreateMeisterData(student);
        MeisterInfo meisterInfo = meisterData.getMeisterInfo();
        meisterInfo.privateCheck(user.getEmail());

        meisterAuthProvider.login(student, dto.getPw().isEmpty() ? student.getStudentId() : dto.getPw());
        MeisterDetailResponse detailInfo = meisterProvider.getAllInfo(student);

        if (meisterInfo.isLoginError()) {
            meisterInfo.setLoginError(false);
            meisterInfoRepository.save(meisterInfo);
        }

        meisterData.setModifiedAt(LocalDateTime.now());
        meisterData.setScores(detailInfo);
        meisterData.setScoreRawData(detailInfo.getScoreHtmlContent());
        meisterData.setPositivePoint(detailInfo.getPositivePoint());
        meisterData.setNegativePoint(detailInfo.getNegativePoint());
        meisterData.setPointRawData(detailInfo.getPointHtmlContent());

        meisterDataRepository.save(meisterData);
        return detailInfo;
    }

    public MeisterResAndAvg get(User user) {
        MeisterData meisterData = meisterDataRepository.findByStudentIdAndModifiedAtGreaterThan(meisterInfoFacade.getMeisterInfo(user).getStudentId(), LocalDate.now().atStartOfDay())
                .orElseGet(() -> meisterDataProvider.getAndUpdateMeisterData(
                        meisterDataProvider.findOrElseCreateMeisterData(getStudent(user))
                ));
        MeisterInfo meisterInfo = meisterData.getMeisterInfo();

        if (meisterInfo.isLoginError()) {
            MeisterResponse build = MeisterResponse.builder()
                    .uniqNo(meisterInfo.getStudentId())
                    .lastUpdate(LocalDateTime.now())
                    .loginError(true)
                    .build();

            return new MeisterResAndAvg(build, meisterDataRepository.findAvgByGradeOfScores(user.getGrade()));
        }

        MeisterResponse build = MeisterResponse.builder()
                .score(meisterData.getScore())
                .positivePoint(meisterData.getPositivePoint())
                .negativePoint(meisterData.getNegativePoint())
                .lastUpdate(meisterData.getModifiedAt())
                .basicJobSkills(meisterData.getBasicJobSkills())
                .professionalTech(meisterData.getProfessionalTech())
                .workEthic(meisterData.getWorkEthic())
                .humanities(meisterData.getHumanities())
                .foreignScore(meisterData.getForeignScore())
                .loginError(false)
                .build();

        return new MeisterResAndAvg(build, meisterDataRepository.findAvgByGradeOfScores(user.getGrade()));
    }

    public MeisterResponse updateAndGet(User user) {
        MeisterData meisterData = meisterDataProvider.getAndUpdateMeisterData(
                meisterDataProvider.findOrElseCreateMeisterData(getStudent(user))
        );
        MeisterInfo meisterInfo = meisterData.getMeisterInfo();

        if (meisterInfo.isLoginError()) {
            return MeisterResponse.builder()
                    .uniqNo(meisterInfo.getStudentId())
                    .lastUpdate(LocalDateTime.now())
                    .loginError(true)
                    .build();
        }

        return MeisterResponse.builder()
                .score(meisterData.getScore())
                .positivePoint(meisterData.getPositivePoint())
                .negativePoint(meisterData.getNegativePoint())
                .lastUpdate(meisterData.getModifiedAt())
                .loginError(false)
                .build();
    }

    private Student getStudent(User user) {
        return studentRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new BsmException(ErrorCode.STUDENT_NOT_FOUND));
    }

}
