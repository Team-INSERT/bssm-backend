package com.insert.ogbsm.service.meister;

import com.insert.ogbsm.domain.meister.MeisterInfo;
import com.insert.ogbsm.domain.meister.repository.MeisterDataRepository;
import com.insert.ogbsm.domain.meister.repository.MeisterInfoRepository;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterRankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeisterRankingService {

    private final MeisterInfoRepository meisterInfoRepository;
    private final MeisterDataRepository meisterDataRepository;
    private final MeisterInfoFacade meisterInfoFacade;

    public void updatePrivateRanking(User user, boolean privateRanking) {
        MeisterInfo meisterInfo = meisterInfoRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new BsmException(ErrorCode.MEISTER_INFO_NOT_FOUND));

        LocalDateTime availableTime = meisterInfo.getLastPrivateDate().plusDays(1);
        if (LocalDateTime.now().isBefore(availableTime)) {
            throw new BsmException(ErrorCode.MEISTER_CANNOT_CHANGE_AUTH);
        }

        meisterInfo.setLastPrivateDate(LocalDateTime.now());
        meisterInfo.setPrivateRanking(privateRanking);
        meisterInfoRepository.save(meisterInfo);
    }

    public List<MeisterRankingResponse> getRanking(User user, int grade) {
        meisterInfoFacade.viewPermissionCheckRank(user);
        return meisterDataRepository.findByMeisterInfoStudentGradeOrderByScoreDesc(grade);
    }

    public Integer getRankingOne(User user) {
        List<MeisterRankingResponse> ranking = getRanking(user, user.getGrade());
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
}
