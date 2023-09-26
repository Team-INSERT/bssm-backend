package com.insert.ogbsm.presentation.comment.dto;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.user.dto.UserSimpleRes;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentRes {
    final Long id;
    final UserSimpleRes user;
    final String detail;
    final int reCommentCount;
    final Long postId;
    final int likeCount;
    final LocalDateTime createdAt;
    final boolean doesLike;


    public CommentRes(Comment comment, User user, boolean doesLike) {
        this.id = comment.getId();
        this.detail = comment.getDetail();
        this.reCommentCount = comment.getReCommentCount();
        this.postId = comment.getPostId();
        this.likeCount = comment.getLikeCount();
        this.createdAt = comment.getCreatedAt();
        this.user = new UserSimpleRes(user);
        this.doesLike = doesLike;
    }

    public record CommentDefRes(Long postId) {}
}
