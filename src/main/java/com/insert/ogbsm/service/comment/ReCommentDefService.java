package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.ReComment;
import com.insert.ogbsm.domain.comment.repo.ReCommentRepo;
import com.insert.ogbsm.presentation.comment.dto.ReCommentReq;
import com.insert.ogbsm.service.validation.UserValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReCommentDefService {
    private final ReCommentRepo reCommentRepo;
    private final UserValidation userValidation;

    public void create(ReCommentReq dto, Long commentId, Long userId) {
        ReComment reComment = new ReComment(dto.detail(), commentId, userId);
        reCommentRepo.save(reComment);
    }

    public void update(ReCommentReq dto, Long userId) {
        ReComment reComment = reCommentRepo.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Updatable ReComment Not Found"));

        userValidation.checkSameUser(userId, reComment.getUserId());

        reComment.update(dto.detail());
    }

    public void delete(Long reCommentId, Long userId) {
        ReComment reComment = reCommentRepo.findById(reCommentId)
                .orElseThrow(() -> new EntityNotFoundException("Deletable ReComment Not Found"));

        userValidation.checkSameUser(reComment.getUserId(), userId);

        reCommentRepo.delete(reComment);
    }
}
