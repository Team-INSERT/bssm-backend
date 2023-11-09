package com.insert.ogbsm.service.like.implement;

import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.domain.like.type.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesImplement {
    private final LikesRepo likesRepo;
    public Optional<Likes> readByUserIdAndPartyIdAndType(Long userId, Long id, Type type) {
        return likesRepo.findByUserIdAndPartyIdAndType(userId, id, type);
    }
}
