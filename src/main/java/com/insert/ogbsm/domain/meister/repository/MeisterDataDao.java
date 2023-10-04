package com.insert.ogbsm.domain.meister.repository;

import com.insert.ogbsm.presentation.meister.dto.response.MeisterScoreResponse;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterRankingResponse;

import java.util.List;

public interface MeisterDataDao {
    List<MeisterRankingResponse> findByMeisterInfoStudentGradeOrderByScoreDesc(int grade);

    MeisterScoreResponse findAvgByGradeOfScores(int grade);

    MeisterScoreResponse findMaxByGradeOfScores(int grade);
}
