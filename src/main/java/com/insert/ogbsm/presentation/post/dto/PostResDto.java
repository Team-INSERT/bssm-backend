package com.insert.ogbsm.presentation.post.dto;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;

public class PostResDto {

    final Long id;
    final String title;
    final Category category;
    final String content;
    final String prUrl;
    final Boolean isFinished;
    final String lostThingImage;

    public PostResDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.content = post.getContent();
        this.prUrl = post.getCodeReview().getPrUrl();
        this.isFinished = post.getCodeReview().getIsFinished();
        this.lostThingImage = post.getLostFound().getLostThingImage();
    }
}
