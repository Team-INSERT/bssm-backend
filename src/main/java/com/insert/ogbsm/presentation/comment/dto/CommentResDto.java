package com.insert.ogbsm.presentation.comment.dto;

import com.insert.ogbsm.domain.comment.Comment;

public class CommentResDto {
    final Long id;

    final String detail;

    final boolean hasReComment;

    final Long postId;

    final int likeCount;

    final Long userId;

    public CommentResDto(Comment comment) {
        this.id = comment.getId();
        this.detail = comment.getDetail();
        this.hasReComment = comment.isHasReComment();
        this.postId = comment.getPostId();
        this.likeCount = comment.getLikeCount();
        this.userId = comment.getUserId();
    }
}
