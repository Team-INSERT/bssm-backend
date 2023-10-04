package com.insert.ogbsm.domain.meister.repository;

import com.insert.ogbsm.presentation.meister.dto.response.MeisterAvgResponse;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterRankingResponse;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResponse;

import java.util.List;

public interface MeisterDataDao {
    List<MeisterRankingResponse> findByMeisterInfoStudentGradeOrderByScoreDesc(int grade);

    MeisterAvgResponse findAvgByGradeOfScores(int grade);
}
