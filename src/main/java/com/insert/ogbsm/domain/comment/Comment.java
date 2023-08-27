package com.insert.ogbsm.domain.comment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detail;

    private boolean hasReComment;

    private int likeCount;

    //TODO 댓글 작성자

    //TODO 나중에 댓글에 작성자 추가함
    public Comment(String detail) {
        this.detail = detail;
    }
}
