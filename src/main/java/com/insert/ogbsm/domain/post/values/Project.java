package com.insert.ogbsm.domain.post.values;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(length = 30)
    private String field;
    //TODO TECH

    public Project(LocalDateTime startTime, LocalDateTime endTime, String field) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.field = field;
    }

    public void update(LocalDateTime startTime, LocalDateTime endTime, String field) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.field = field;
    }
}
