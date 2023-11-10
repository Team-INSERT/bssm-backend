package com.insert.ogbsm.service.comment.business;

import com.insert.ogbsm.domain.comment.ReComment;
import com.insert.ogbsm.presentation.comment.dto.ReCommentReq;
import com.insert.ogbsm.presentation.comment.dto.ReCommentRes;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.service.comment.implement.ReCommentImplement;
import com.insert.ogbsm.service.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReCommentBusiness {
    private final ReCommentImplement reCommentImplement;
    private final UserValidation userValidation;

    public void create(ReCommentReq dto, Long commentId, Long userId) {
        ReComment reComment = new ReComment(dto.detail(), commentId, userId);
        reCommentImplement.increaseReCommentCount(commentId);
        reCommentImplement.append(reComment);
    }

    public void update(ReCommentReq dto, Long userId) {
        ReComment reComment = reCommentImplement.readById(dto.id());
        userValidation.mustBeSameUser(userId, reComment.getUserId());
        reComment.update(dto.detail());
    }

    public void delete(Long reCommentId, Long userId) {
        ReComment reComment = reCommentImplement.readById(reCommentId);
        reCommentImplement.decreaseReCommentCount(reComment);
        userValidation.mustBeSameUser(reComment.getUserId(), userId);
        reCommentImplement.remove(reComment);
    }

    public Pagination<List<ReCommentRes>> read(Long commentId, Pageable pageable, Long userId) {
        return reCommentImplement.readByCommentId(commentId, userId, pageable);
    }
}
