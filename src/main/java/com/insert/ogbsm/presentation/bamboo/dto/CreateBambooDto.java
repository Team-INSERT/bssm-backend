package com.insert.ogbsm.presentation.bamboo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateBambooDto {
    @NotNull
    private String content;
}
