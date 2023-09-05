package com.insert.ogbsm.presentation.bamboo.dto;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AllowedBambooRes {
    private final Long allowedId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime allowedAt;

    public AllowedBambooRes(AllowedBamboo allowedBamboo) {
        this.allowedId = allowedBamboo.getId();
        this.content = allowedBamboo.getBamboo().getContent();
        this.createdAt = allowedBamboo.getBamboo().getCreatedAt();
        this.allowedAt = allowedBamboo.getCreatedAt();
    }
}
