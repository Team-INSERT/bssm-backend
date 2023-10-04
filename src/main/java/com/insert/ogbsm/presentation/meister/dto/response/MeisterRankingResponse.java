package com.insert.ogbsm.presentation.meister.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

public record MeisterRankingResponse(
        Float score,
        Integer positivePoint,
        Integer negativePoint,
        LocalDateTime lastUpdate,
        MeisterStudentResponse student
) {

    @QueryProjection
    public MeisterRankingResponse {
    }
}
