package com.insert.ogbsm.service.meister.implement;

import com.insert.ogbsm.domain.meister.MeisterData;
import com.insert.ogbsm.domain.meister.MeisterInfo;
import com.insert.ogbsm.domain.meister.repository.MeisterDataRepository;
import com.insert.ogbsm.domain.meister.repository.MeisterInfoRepository;
import com.insert.ogbsm.domain.user.Student;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.StudentRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterDetailResponse;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterRankingResponse;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MeisterImplement {

    private final MeisterInfoRepository meisterInfoRepository;
    private final StudentRepo studentRepo;
    private final MeisterDataRepository meisterDataRepository;
    private final MeisterDataProvider meisterDataProvider;
    private final MeisterAuthProvider meisterAuthProvider;
    private final MeisterParsingProvider meisterProvider;

    public MeisterData readMeisterData(User user) {
        return meisterDataRepository.findByStudentIdAndModifiedAtGreaterThan(getMeisterInfo(user).getStudentId(), LocalDate.now().atStartOfDay())
                .orElseGet(() -> meisterDataProvider.getAndUpdateMeisterData(
                        meisterDataProvider.findOrElseCreateMeisterData(getStudent(user))
                ));
    }

    private Student getStudent(User user) {
        return studentRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new BsmException(ErrorCode.STUDENT_NOT_FOUND));
    }


    public MeisterInfo getMeisterInfo(User user) {
        return meisterInfoRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new BsmException(ErrorCode.MEISTER_INFO_NOT_FOUND));
    }

    public MeisterInfo readInfoByEmail(String email) {
        return meisterInfoRepository.findByEmail(email)
                .orElseThrow(() -> new BsmException(ErrorCode.MEISTER_INFO_NOT_FOUND));
    }

    public List<MeisterRankingResponse> readRanking(int grade) {
        return meisterDataRepository.findByMeisterInfoStudentGradeOrderByScoreDesc(grade);
    }

    public Integer getRankingOf(User user, List<MeisterRankingResponse> ranking) {
        int rank = 0;
        boolean changed = false;
        for (MeisterRankingResponse meisterRankingResponse : ranking) {
            rank++;

            if (meisterRankingResponse.student().studentNo() == user.getStudent_number() &&
                    meisterRankingResponse.student().classNo() == user.getClass_number() &&
                    meisterRankingResponse.student().grade() == user.getGrade()) {
                changed = true;
                break;
            }
        }

        if (!changed) {
            return 0;
        }

        return rank;
    }

    public MeisterData readMiesterData(Student student) {
        return meisterDataProvider.findOrElseCreateMeisterData(student);
    }

    public void tryLogin(Student student, MeisterInfo meisterInfo, String password) throws IOException {
        meisterAuthProvider.login(student, password.isEmpty() ? student.getStudentId() : password);

        if (meisterInfo.isLoginError()) {
            meisterInfo.setLoginError(false);
            meisterInfoRepository.save(meisterInfo);
        }
    }

    public MeisterDetailResponse updateMeisterDetail(Student student, MeisterData meisterData) throws IOException {
        MeisterDetailResponse detailInfo = meisterProvider.getAllInfo(student);

        meisterData.setModifiedAt(LocalDateTime.now());
        meisterData.setScores(detailInfo);
        meisterData.setScoreRawData(detailInfo.getScoreHtmlContent());
        meisterData.setPositivePoint(detailInfo.getPositivePoint());
        meisterData.setNegativePoint(detailInfo.getNegativePoint());
        meisterData.setPointRawData(detailInfo.getPointHtmlContent());

        meisterDataRepository.save(meisterData);
        return detailInfo;
    }



    public MeisterResAndAvgAndMax getErrorStudentResWithAvg(User user, MeisterInfo meisterInfo) {
        MeisterResponse build = getErrorStudentRes(meisterInfo);

        return new MeisterResAndAvgAndMax(
                build,
                meisterDataRepository.findAvgByGradeOfScores(user.getGrade()),
                meisterDataRepository.findMaxByGradeOfScores(user.getGrade()));
    }

    public MeisterResponse getErrorStudentRes(MeisterInfo meisterInfo) {
        return MeisterResponse.builder()
                .uniqNo(meisterInfo.getStudentId())
                .lastUpdate(LocalDateTime.now())
                .loginError(true)
                .build();
    }

    public MeisterResAndAvgAndMax getNormalStudentResWithAvg(User user, MeisterData meisterData) {
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

        return new MeisterResAndAvgAndMax(
                build,
                meisterDataRepository.findAvgByGradeOfScores(user.getGrade()),
                meisterDataRepository.findMaxByGradeOfScores(user.getGrade())
        );
    }

    public MeisterResponse getNormalStudentRes(MeisterData meisterData) {
        return MeisterResponse.builder()
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
    }

    public MeisterData getNewMeisterData(User user) {
        return meisterDataProvider.getAndUpdateMeisterData(
                meisterDataProvider.findOrElseCreateMeisterData(getStudent(user))
        );
    }
}
