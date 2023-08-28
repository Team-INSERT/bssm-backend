package com.insert.ogbsm.presentation.comment.dto;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.user.dto.UserSimpleRes;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResDto {
    final Long id;
    final UserSimpleRes user;
    final String detail;
    final boolean hasReComment;
    final Long postId;
    final int likeCount;
    final LocalDateTime createdAt;


    public CommentResDto(Comment comment, User user) {
        this.id = comment.getId();
        this.detail = comment.getDetail();
        this.hasReComment = comment.isHasReComment();
        this.postId = comment.getPostId();
        this.likeCount = comment.getLikeCount();
        this.createdAt = comment.getCreatedAt();
        this.user = new UserSimpleRes(user);
    }
}
