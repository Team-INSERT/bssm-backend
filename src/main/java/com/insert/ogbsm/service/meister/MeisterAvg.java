package com.insert.ogbsm.service.meister;

import com.insert.ogbsm.presentation.meister.dto.response.MeisterDetailResponse;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterScoreResponse;

public record MeisterAvg(
        MeisterDetailResponse meister,
        MeisterScoreResponse avg,
        MeisterScoreResponse max
) {
}
