package com.insert.ogbsm.presentation.meister.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeisterResponse {

    private Float score;
    private Integer positivePoint;
    private Integer negativePoint;
    private LocalDateTime lastUpdate;
    private String uniqNo;
    private Boolean loginError;

    private float basicJobSkills;
    private float professionalTech;
    private float workEthic;
    private float humanities;
    private float foreignScore;
}
