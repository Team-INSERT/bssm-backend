package com.insert.ogbsm.presentation.bamboo.dto;

import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public record CreateBambooReq(
        String content
) {
    public Bamboo of(Long userId) {
        return Bamboo.builder()
                .content(content)
                .userId(SecurityUtil.getCurrentUserWithLogin().getId())
                .isAllow(false)
                .build();
    }
}
