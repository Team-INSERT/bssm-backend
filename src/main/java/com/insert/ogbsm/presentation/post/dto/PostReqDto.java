package com.insert.ogbsm.presentation.post.dto;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.values.CodeReview;
import com.insert.ogbsm.domain.post.values.LostFound;
import com.insert.ogbsm.domain.post.values.Project;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class PostReqDto {

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


    public PostReqDto(Long id, String title, Category category, String content, String prUrl, Boolean isFinished, String lostThingImage, LocalDateTime startTime, LocalDateTime endTime, String field) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.prUrl = prUrl;
        this.isFinished = isFinished;
        this.lostThingImage = lostThingImage;
        this.startTime = startTime;
        this.endTime = endTime;
        this.field = field;
    }

    public Post entityToBeCreated() {
        Post post = new Post(title, category, content);
        post.setCodeReview(new CodeReview(prUrl, isFinished));
        post.setLostFound(new LostFound(lostThingImage));
        post.setProject(new Project(startTime, endTime, field));

        return post;
    }

    public Post entityToBeUpdated() {
        Post post = new Post(title, category, content);
        post.setCodeReview(new CodeReview(prUrl, isFinished));
        post.setLostFound(new LostFound(lostThingImage));
        post.setProject(new Project(startTime, endTime, field));
        post.setIdForUpdate(id);

        return post;
    }
}
