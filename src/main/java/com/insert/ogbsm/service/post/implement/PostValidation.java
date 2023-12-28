package com.insert.ogbsm.service.post.implement;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
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

    public void mustBeLostOrFound(Post post) {
        if (post.getCategory() != Category.LOST && post.getCategory() != Category.FOUND) {
            throw new BsmException(ErrorCode.POST_TYPE_WEIRD);
        }
    }
}
