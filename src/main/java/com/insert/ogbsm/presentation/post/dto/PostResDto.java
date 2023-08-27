package com.insert.ogbsm.presentation.post.dto;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;

import java.time.LocalDateTime;

public class PostResDto {

    final Long id;
    final String title;
    final Category category;
    final String content;
    String prUrl;
    Boolean isFinished;
    String lostThingImage;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String field;

    public PostResDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.content = post.getContent();
        if (category == Category.CODE_REVIEW) {
            this.prUrl = post.getCodeReview().getPrUrl();
            this.isFinished = post.getCodeReview().getIsFinished();
        }
        if (category == Category.LOST_FOUND) {
            this.lostThingImage = post.getLostFound().getLostThingImage();
        }
        if (category == Category.PROJECT) {
            this.startTime = post.getProject().getStartTime();
            this.endTime = post.getProject().getEndTime();
            this.field = post.getProject().getField();
        }
    }
}
