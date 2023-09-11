package com.insert.ogbsm.presentation.like.dto;

import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.type.Type;

public record LikesReq(Type type, Long partyId) {
    public Likes toEntity(Long userId) {
        return new Likes(userId, type, partyId);
    }
}
