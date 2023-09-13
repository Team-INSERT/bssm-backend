package com.insert.ogbsm.service.validation;

import com.insert.ogbsm.domain.post.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidation {
    private final PostRepo postRepo;

    public void checkPostExist(Long postId) {
        postRepo.findById(postId);
//                .orElseThrow(() -> new BsmException(ErrorCode.INVALID_TOKEN));
    }

}
