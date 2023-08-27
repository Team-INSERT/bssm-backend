package com.insert.ogbsm.domain.comment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detail;

    private Long commentId;

    private int likeCount;

    public ReComment(String detail, Long commentId) {
        this.detail = detail;
        this.commentId = commentId;
        likeCount = 0;
    }
}
