package com.insert.ogbsm.presentation.post.dto;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.values.CodeReview;
import com.insert.ogbsm.domain.post.values.LostFound;
import com.insert.ogbsm.domain.post.values.Project;

import java.time.LocalDateTime;

public record PostReqDto(Long id, String title, Category category, String content, String prUrl, Boolean isFinished,
                         String lostThingImage, LocalDateTime startTime, LocalDateTime endTime, String field) {

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
