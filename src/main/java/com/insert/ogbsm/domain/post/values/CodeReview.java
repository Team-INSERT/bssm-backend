package com.insert.ogbsm.domain.post.values;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeReview {

    @Column(columnDefinition = "TEXT")
    private String prUrl;

    @Column(columnDefinition = "boolean default false")
    private Boolean isFinished;

    public CodeReview(String prUrl, Boolean isFinished) {
        this.prUrl = prUrl;
        this.isFinished = isFinished;
    }

    public void update(String prUrl, boolean isFinished) {
        this.prUrl = prUrl;
        this.isFinished = isFinished;
    }
}
