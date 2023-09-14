package com.insert.ogbsm.presentation.post.dto;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.user.dto.UserSimpleRes;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostRes {

    final Long id;
    final String title;
    final Category category;
    final String content;
    final UserSimpleRes user;
    final LocalDateTime createdAt;
    final int likeCount;
    final int commentCount;
    UserSimpleRes foundUser;
    String prUrl;
    Boolean isFinished;
    String lostThingImage;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String field;

    public PostRes(Post post, User user) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.content = post.getContent();
        this.user = new UserSimpleRes(user);
        this.createdAt = post.getCreatedAt();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        if (category == Category.CODE_REVIEW) {
            this.prUrl = post.getCodeReview().getPrUrl();
            this.isFinished = post.getCodeReview().getIsFinished();
        }
        if (category == Category.LOST || category == Category.FOUND) {
            this.lostThingImage = post.getLostFound().getLostThingImage();
            foundUser = null;
        }

        if (category == Category.PROJECT) {
            this.startTime = post.getProject().getStartTime();
            this.endTime = post.getProject().getEndTime();
            this.field = post.getProject().getField();
        }
    }

    public PostRes(Post post, User user, User foundUser) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.content = post.getContent();
        this.user = new UserSimpleRes(user);
        this.createdAt = post.getCreatedAt();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();

        this.lostThingImage = post.getLostFound().getLostThingImage();

        if (foundUser != null) {
            this.foundUser = new UserSimpleRes(foundUser);
        }
    }
}
