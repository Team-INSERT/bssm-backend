package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostWrapper;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.comment.dto.CommentReq;
import com.insert.ogbsm.service.validation.PostValidation;
import com.insert.ogbsm.service.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.insert.ogbsm.presentation.comment.dto.CommentRes.*;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentDefService {
    private final CommentRepo commentRepo;
    private final PostWrapper postWrapper;
    private final PostValidation postValidation;
    private final UserValidation userValidation;

    public CommentDefRes create(CommentReq reqDto, Long postId, Long userId) {

        Comment comment = new Comment(reqDto.detail(), postId, userId);

        postValidation.checkPostExist(postId);
        increaseCommentCount(postId);
        commentRepo.save(comment);

        return new CommentDefRes(comment.getPostId());
    }

    private void increaseCommentCount(Long postId) {
        Post post = postWrapper.getPost(postId);

        post.increaseCommentCount();
    }

    public CommentDefRes update(CommentReq reqDto, Long userId) {
        Comment comment = commentRepo.findById(reqDto.id())
                .orElseThrow(() -> new BsmException(ErrorCode.COMMENT_NOT_FOUND));

        userValidation.checkSameUser(userId, comment.getUserId());

        comment.update(reqDto.detail());

        return new CommentDefRes(comment.getPostId());
    }

    public CommentDefRes delete(Long commentId, Long userId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new BsmException(ErrorCode.COMMENT_NOT_FOUND));

        userValidation.checkSameUser(userId, comment.getUserId());

        decreaseCommentCount(comment);
        commentRepo.delete(comment);

        return new CommentDefRes(comment.getPostId());
    }

    private void decreaseCommentCount(Comment comment) {
        Post post = postWrapper.getPost(comment.getPostId());

        post.decreaseCommentCount();
    }
}
