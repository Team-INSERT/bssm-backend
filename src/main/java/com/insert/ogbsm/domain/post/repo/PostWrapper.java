package com.insert.ogbsm.domain.post.repo;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostWrapper {

    private final PostRepo postRepo;

    public Post getPost(Long postId) {
        return postRepo.findById(postId)
                .orElseThrow(() -> new BsmException(ErrorCode.POST_NOT_FOUND));
    }
}
