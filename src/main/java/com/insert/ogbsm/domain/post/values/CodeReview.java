package com.insert.ogbsm.domain.post.values;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class CodeReview {

    @Column(columnDefinition = "TEXT")
    private String prUrl;

    @Column(columnDefinition = "boolean default false")
    private boolean isFinished;

    public CodeReview(String prUrl, boolean isFinished) {
        this.prUrl = prUrl;
        this.isFinished = isFinished;
    }

    public void update(String prUrl, boolean isFinished) {
        this.prUrl = prUrl;
        this.isFinished = isFinished;
    }
}
