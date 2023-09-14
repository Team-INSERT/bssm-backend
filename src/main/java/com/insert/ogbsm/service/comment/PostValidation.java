package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidation {
    private final PostRepo postRepo;

    public void checkPostExist(Long postId) {
        postRepo.findById(postId)
                .orElseThrow(() -> new BsmException(ErrorCode.POST_NOT_FOUND));
    }
}
