package com.insert.ogbsm.service.meister.business;

import com.insert.ogbsm.domain.meister.MeisterInfo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterRankingResponse;
import com.insert.ogbsm.service.meister.implement.MeisterImplement;
import com.insert.ogbsm.service.meister.implement.MeisterValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeisterRankingBusiness {

    private final MeisterImplement meisterImpleMent;
    private final MeisterValidation meisterValidation;

    public void updatePrivateRanking(User user, boolean privateRanking) {
        MeisterInfo meisterInfo = meisterImpleMent.readInfoByEmail(user.getEmail());
        meisterValidation.mustBeAbleToUpdate(meisterInfo);
        meisterInfo.setLastPrivateDate(LocalDateTime.now());
        meisterInfo.setPrivateRanking(privateRanking);
    }

    public List<MeisterRankingResponse> getRanking(User user, int grade) {
        meisterValidation.viewPermissionCheckRank(user);
        return meisterImpleMent.readRanking(grade);
    }

    public Integer getRankingOne(User user) {
        List<MeisterRankingResponse> ranking = getRanking(user, user.getGrade());
        return meisterImpleMent.getRankingOf(user, ranking);
    }
}
