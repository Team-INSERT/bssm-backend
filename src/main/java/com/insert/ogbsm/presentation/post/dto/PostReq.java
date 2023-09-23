package com.insert.ogbsm.presentation.post.dto;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.values.CodeReview;
import com.insert.ogbsm.domain.post.values.LostFound;
import com.insert.ogbsm.domain.post.values.Project;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;

import java.time.LocalDateTime;

public record PostReq(Long id, String title, Category category, String content, String prUrl, Boolean isFinished,
                      String lostThingImage, String place, String keepingPlace, LocalDateTime startTime,
                      LocalDateTime endTime, String field) {

    public PostReq(Long id, String title, Category category, String content, String prUrl, Boolean isFinished, String lostThingImage, String place, String keepingPlace, LocalDateTime startTime, LocalDateTime endTime, String field) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;

        validateAllExist(category, prUrl, isFinished, startTime, endTime, field, place, keepingPlace);

        this.prUrl = prUrl;
        this.isFinished = isFinished;
        this.lostThingImage = lostThingImage;
        this.startTime = startTime;
        this.endTime = endTime;
        this.field = field;
        this.place = place;
        this.keepingPlace = keepingPlace;
    }

    private void validateAllExist(Category category, String prUrl, Boolean isFinished, LocalDateTime startTime, LocalDateTime endTime, String field, String place, String keepingPlace) {
        if (category == Category.CODE_REVIEW) {
            if (prUrl == null || isFinished == null) {
                throw new BsmException(ErrorCode.POST_VALUE_NOT_EXIST);
            }
        } else if (category == Category.PROJECT) {
            if (startTime == null || endTime == null || field == null) {
                throw new BsmException(ErrorCode.POST_VALUE_NOT_EXIST);
            }
        } else if (category == Category.LOST) {
            if (place == null) {
                throw new BsmException(ErrorCode.POST_VALUE_NOT_EXIST);
            }
        } else if (category == Category.FOUND) {
            if (place == null || keepingPlace == null) {
                throw new BsmException(ErrorCode.POST_VALUE_NOT_EXIST);
            }
        }
    }

    public Post entityToBeCreated(Long userId) {
        Post post = new Post(title, category, content, userId);
        post.setCodeReview(new CodeReview(prUrl, isFinished));
        post.setLostFound(new LostFound(lostThingImage, place, keepingPlace));
        post.setProject(new Project(startTime, endTime, field));

        return post;
    }

    public Post entityToBeUpdated(Long userId) {
        Post post = new Post(title, category, content, userId);
        post.setCodeReview(new CodeReview(prUrl, isFinished));
        post.setLostFound(new LostFound(lostThingImage, place, keepingPlace));
        post.setProject(new Project(startTime, endTime, field));
        post.setIdForUpdate(id);

        return post;
    }
}
