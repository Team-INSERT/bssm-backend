package com.insert.ogbsm.presentation.bamboo.dto;

import com.insert.ogbsm.domain.bamboo.Bamboo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BambooRes {
    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;

    public BambooRes(Bamboo bamboo) {
        this.id = bamboo.getId();
        this.content = bamboo.getContent();
        this.createdAt = bamboo.getCreatedAt();
    }
}
