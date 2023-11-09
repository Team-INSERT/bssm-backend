package com.insert.ogbsm.service.post.implement;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostImplement {
    private final PostRepo postRepo;

    public Post readPost(Long postId) {
        return postRepo.findById(postId)
                .orElseThrow(() -> new BsmException(ErrorCode.POST_NOT_FOUND));
    }
}
