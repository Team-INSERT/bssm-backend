package com.insert.ogbsm.service.meister.implement;

import com.insert.ogbsm.domain.meister.MeisterData;
import com.insert.ogbsm.domain.meister.MeisterInfo;
import com.insert.ogbsm.domain.meister.repository.MeisterDataRepository;
import com.insert.ogbsm.domain.meister.repository.MeisterInfoRepository;
import com.insert.ogbsm.domain.user.Student;
import com.insert.ogbsm.domain.user.repo.StudentRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MeisterDataProvider {

    private final StudentRepo studentRepo;
    private final MeisterInfoRepository meisterInfoRepository;
    private final MeisterDataRepository meisterDataRepository;
    private final MeisterParsingProvider meisterProvider;
    private final MeisterAuthProvider meisterAuthProvider;

    public MeisterData findOrElseCreateMeisterData(Student student) {
        return meisterDataRepository.findById(student.getStudentId()).orElseGet(
                () -> {
                    MeisterInfo meisterInfo = meisterInfoRepository.save(
                            MeisterInfo.builder()
                                    .studentId(student.getStudentId())
                                    .lastPrivateDate(LocalDateTime.now())
                                    .email(student.getEmail())
                                    .build()
                    );
                    return MeisterData.builder()
                            .studentId(student.getStudentId())
                            .meisterInfo(meisterInfo)
                            .build();
                }
        );
    }

    public MeisterData getAndUpdateMeisterData(MeisterData meisterData) {
        MeisterInfo meisterInfo = meisterData.getMeisterInfo();

        MeisterDetailResponse responseDto;
        try {
            meisterData.setModifiedAt(LocalDateTime.now());
            meisterAuthProvider.login(getStudent(meisterInfo), meisterInfo.getStudentId());
            responseDto = meisterProvider.getAllInfo(getStudent(meisterInfo));
        } catch (BsmException e) {
            try {
                meisterInfo.setLoginError(true);
                responseDto = meisterProvider.getScoreInfo(getStudent(meisterInfo));
                meisterData.setScores(responseDto);
                meisterData.setScoreRawData(responseDto.getScoreHtmlContent());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            meisterInfoRepository.save(meisterInfo);
            return meisterDataRepository.save(meisterData);
        } catch (IOException e) {
            throw new BsmException(ErrorCode.MEISTER_INTERNAL_SERVER);
        }

        if (meisterInfo.isLoginError()) {
            meisterInfo.setLoginError(false);
            meisterInfoRepository.save(meisterInfo);
        }
        meisterData.setScores(responseDto);
        meisterData.setScoreRawData(responseDto.getScoreHtmlContent());
        meisterData.setPositivePoint(responseDto.getPositivePoint());
        meisterData.setNegativePoint(responseDto.getNegativePoint());
        meisterData.setPointRawData(responseDto.getPointHtmlContent());

        return meisterDataRepository.save(meisterData);
    }

    private Student getStudent(MeisterInfo meisterInfo) {
        return studentRepo.findByEmail(meisterInfo.getEmail())
                .orElse(null);
    }

}
