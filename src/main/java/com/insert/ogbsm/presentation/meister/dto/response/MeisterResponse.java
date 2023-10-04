package com.insert.ogbsm.presentation.meister.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

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
}
