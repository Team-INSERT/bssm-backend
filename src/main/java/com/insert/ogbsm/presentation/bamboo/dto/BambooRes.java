package com.insert.ogbsm.presentation.bamboo.dto;

import com.insert.ogbsm.domain.bamboo.Bamboo;

import java.time.LocalDateTime;

public class BambooRes {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    public BambooRes(Bamboo bamboo) {
        this.id = bamboo.getId();
        this.content = bamboo.getContent();
        this.createdAt = bamboo.getCreatedAt();
    }
}
