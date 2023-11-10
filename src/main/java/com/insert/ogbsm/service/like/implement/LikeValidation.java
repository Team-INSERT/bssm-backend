package com.insert.ogbsm.service.like.implement;

import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.domain.comment.repo.ReCommentRepo;
import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.type.Type;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.service.post.implement.PostValidation;
import com.insert.ogbsm.service.user.implement.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeValidation {
    private final UserValidation userValidation;
    private final PostValidation postValidation;
    private final CommentRepo commentRepo;
    private final ReCommentRepo reCommentRepo;

    public void beforeCreating(Likes likes) {
        userValidation.checkUserExist(likes.getUserId());

        Type type = likes.getType();
        Long partyId = likes.getPartyId();

        if (type == Type.POST) {
            postValidation.checkPostExist(partyId);
        } else if (type == Type.COMMENT) {
            commentRepo.findById(partyId)
                    .orElseThrow(() -> new BsmException(ErrorCode.COMMENT_NOT_FOUND));
        } else if (type == Type.RECOMMENT) {
            reCommentRepo.findById(partyId)
                    .orElseThrow(() -> new BsmException(ErrorCode.RECOMMENT_NOT_FOUND));
        }
    }
}
