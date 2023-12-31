package com.insert.ogbsm.presentation.meister.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record MeisterScoreResponse(
        double score,
        double basicJobSkills,
        double professionalTech,
        double workEthic,
        double humanities,
        double foreignScore,
        double positivePoint,
        double negativePoint
) {
    @QueryProjection
    public MeisterScoreResponse {
    }
}
