package com.insert.ogbsm.presentation.meister.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeisterDetailResponse {

    private String scoreHtmlContent;
    private String pointHtmlContent;
    private float score;
    private int positivePoint;
    private int negativePoint;
    private float basicJobSkills;
    private float professionalTech;
    private float workEthic;
    private float humanities;
    private float foreignScore;
}
