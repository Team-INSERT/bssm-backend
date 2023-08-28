package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.presentation.comment.dto.CommentReqDto;
import com.insert.ogbsm.service.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentDefService {
    private final CommentRepo commentRepo;
    private final UserValidation userValidation;

    public void createComment(CommentReqDto reqDto, Long postId, Long userId) {
        Comment comment = new Comment(reqDto.detail(), postId, userId);
        System.out.println(comment);
        commentRepo.save(comment);
    }

    public void updateComment(CommentReqDto reqDto, Long userId) {
        Comment comment = commentRepo.findById(reqDto.id())
                .orElseThrow(() -> new EntityNotFoundException("No Updatable Comment"));

        userValidation.checkSameUser(userId, comment.getUserId());

        comment.update(reqDto.detail());
    }
}
