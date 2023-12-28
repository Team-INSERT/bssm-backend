package com.insert.ogbsm.service.comment.business;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.presentation.comment.dto.CommentReq;
import com.insert.ogbsm.presentation.comment.dto.CommentRes;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.service.comment.implement.CommentImplement;
import com.insert.ogbsm.service.user.implement.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentBusiness {
    private final CommentImplement commentImplement;
    private final UserValidation userValidation;

    public void create(Comment comment) {
        commentImplement.increaseCommentCount(comment.getPostId());
        commentImplement.append(comment);
    }

    public void update(CommentReq reqDto, Long userId) {
        Comment comment = commentImplement.readById(reqDto.id());
        userValidation.mustBeSameUser(userId, comment.getUserId());
        comment.update(reqDto.detail());
    }

    public void delete(Long commentId, Long userId) {
        Comment comment = commentImplement.readById(commentId);
        userValidation.mustBeSameUser(userId, comment.getUserId());
        commentImplement.decreaseCommentCount(comment.getPostId());
        commentImplement.remove(comment);
    }

    public Pagination<List<CommentRes>> readComments(Long postId, Long userId, Pageable pageable) {
        return commentImplement.indOrderByMineLikeRecent(postId, userId, pageable);
    }
}
