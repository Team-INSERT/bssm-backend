package com.insert.ogbsm.presentation.meister.dto.response;

public record MeisterResAndAvgAndMax(
        MeisterResponse meister,
        MeisterScoreResponse avg,
        MeisterScoreResponse max
) {
}
