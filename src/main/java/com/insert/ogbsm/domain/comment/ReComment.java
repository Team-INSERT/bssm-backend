package com.insert.ogbsm.domain.comment;

import com.insert.ogbsm.domain.common.CreatedAt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReComment extends CreatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detail;

    private Long commentId;

    private int likeCount;

    private Long userId;

    public ReComment(String detail, Long commentId, Long userId) {
        this.detail = detail;
        this.commentId = commentId;
        this.userId = userId;
        likeCount = 0;
    }

    public void update(String detail) {
        this.detail = detail;
    }
}
