package com.insert.ogbsm.presentation.comment.dto;

import com.insert.ogbsm.domain.comment.ReComment;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.user.dto.UserSimpleRes;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReCommentRes {
    final Long id;
    final UserSimpleRes user;
    final String detail;
    final Long commentId;
    final int likeCount;
    final LocalDateTime createdAt;

    public ReCommentRes(ReComment reComment, User user) {
        this.id = reComment.getId();
        this.detail = reComment.getDetail();
        this.commentId = reComment.getCommentId();
        this.likeCount = reComment.getLikeCount();
        this.createdAt = reComment.getCreatedAt();
        this.user = new UserSimpleRes(user);
    }
}
