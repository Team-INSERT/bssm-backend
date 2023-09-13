package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.presentation.comment.dto.CommentReq;
import com.insert.ogbsm.service.validation.UserValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentDefService {
    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserValidation userValidation;

    public void create(CommentReq reqDto, Long postId, Long userId) {
        Comment comment = new Comment(reqDto.detail(), postId, userId);

        increaseCommentCount(postId);

        commentRepo.save(comment);
    }

    private void increaseCommentCount(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        post.increaseCommentCount();
    }

    public void update(CommentReq reqDto, Long userId) {
        Comment comment = commentRepo.findById(reqDto.id())
                .orElseThrow(() -> new EntityNotFoundException("No Updatable Comment"));

        userValidation.checkSameUser(userId, comment.getUserId());

        comment.update(reqDto.detail());
    }

    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment Not Found"));

        userValidation.checkSameUser(userId, comment.getUserId());

        decreaseCommentCount(comment);

        commentRepo.delete(comment);
    }

    private void decreaseCommentCount(Comment comment) {
        Post post = postRepo.findById(comment.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        post.decreaseCommentCount();
    }
}
