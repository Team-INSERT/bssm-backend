package com.insert.ogbsm.presentation.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginDto {
    @NotNull
    private String authCode;
}
