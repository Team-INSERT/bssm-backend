package com.insert.ogbsm.presentation.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UsingRefreshTokenReqDto {

    @NotNull
    private String refreshToken;

}