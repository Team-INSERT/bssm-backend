package com.insert.ogbsm.presentation.post.dto;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class PostResDto {

    final Long id;
    final String title;
    final Category category;
    final String content;
    final String prUrl;
    final Boolean isFinished;
    final String lostThingImage;
    final LocalDateTime startTime;
    final LocalDateTime endTime;
    final String field;

    public PostResDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.content = post.getContent();
        this.prUrl = post.getCodeReview().getPrUrl();
        this.isFinished = post.getCodeReview().getIsFinished();
        this.lostThingImage = post.getLostFound().getLostThingImage();
        this.startTime = post.getProject().getStartTime();
        this.endTime = post.getProject().getEndTime();
        this.field = post.getProject().getField();
    }
}
