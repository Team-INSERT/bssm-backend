package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.ReComment;
import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.domain.comment.repo.ReCommentRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.comment.dto.ReCommentReq;
import com.insert.ogbsm.service.validation.CommentValidation;
import com.insert.ogbsm.service.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.insert.ogbsm.presentation.comment.dto.ReCommentRes.ReCommentDefRes;

@Service
@Transactional
@RequiredArgsConstructor
public class ReCommentDefService {
    private final ReCommentRepo reCommentRepo;
    private final CommentValidation commentValidation;
    private final CommentRepo commentRepo;
    private final UserValidation userValidation;

    public ReCommentDefRes create(ReCommentReq dto, Long commentId, Long userId) {
        ReComment reComment = new ReComment(dto.detail(), commentId, userId);

        commentValidation.checkCommentExist(commentId);
        increaseReCommentCount(commentId);

        reCommentRepo.save(reComment);

        return new ReCommentDefRes(reComment.getCommentId());
    }

    private void increaseReCommentCount(Long commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new BsmException(ErrorCode.COMMENT_NOT_FOUND));

        comment.increaseReCommentCount();
    }

    public ReCommentDefRes update(ReCommentReq dto, Long userId) {
        ReComment reComment = reCommentRepo.findById(dto.id())
                .orElseThrow(() -> new BsmException(ErrorCode.RECOMMENT_NOT_FOUND));

        userValidation.checkSameUser(userId, reComment.getUserId());

        reComment.update(dto.detail());

        return new ReCommentDefRes(reComment.getCommentId());
    }

    public ReCommentDefRes delete(Long reCommentId, Long userId) {
        ReComment reComment = reCommentRepo.findById(reCommentId)
                .orElseThrow(() -> new BsmException(ErrorCode.RECOMMENT_NOT_FOUND));

        decreaseReCommentCount(reComment);
        userValidation.checkSameUser(reComment.getUserId(), userId);
        reCommentRepo.delete(reComment);

        return new ReCommentDefRes(reComment.getCommentId());
    }

    private void decreaseReCommentCount(ReComment reComment) {
        Comment comment = commentRepo.findById(reComment.getCommentId())
                .orElseThrow(() -> new BsmException(ErrorCode.COMMENT_NOT_FOUND));

        comment.decreaseReCommentCount();
    }
}
