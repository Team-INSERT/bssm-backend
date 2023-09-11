package com.insert.ogbsm.service.like;

import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.presentation.like.dto.LikesReq;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeDefService {
    private final LikesRepo likeRepo;

    public boolean changeLikeStatus(LikesReq likesReq, Long userId) {
        System.out.println("asdfasdfasd " + likesReq.partyId() + " " + likesReq.type());
        Optional<Likes> like = likeRepo.findByUserIdAndTypeAndPartyId(userId, likesReq.type(), likesReq.partyId());

        if (like.isEmpty()) {
            Likes likes = likesReq.toEntity(userId);
            likeRepo.save(likes);
            return true;
        }

        likeRepo.delete(like.get());
        return false;
    }
}
