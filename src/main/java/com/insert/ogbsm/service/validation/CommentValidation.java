package com.insert.ogbsm.service.validation;

import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidation {
    private final CommentRepo commentRepo;

    public void checkCommentExist(Long commentId) {
        commentRepo.findById(commentId)
                .orElseThrow(() -> new BsmException(ErrorCode.POST_NOT_FOUND));
    }
}
