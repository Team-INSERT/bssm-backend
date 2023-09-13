package com.insert.ogbsm.domain.comment;

import com.insert.ogbsm.domain.common.CreatedAt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends CreatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private Long postId;

    private int likeCount = 0;

    private int reCommentCount = 0;

    private Long userId;

    public Comment(String detail, Long postId, Long userId) {
        this.detail = detail;
        this.postId = postId;
        this.likeCount = 0;
        this.userId = userId;
    }

    public void update(String detail) {
        this.detail = detail;
    }

    public void increaseLike() {
        likeCount++;
    }

    public void decreaseLike() {
        likeCount--;
    }

    public void increaseReCommentCount() {
        reCommentCount++;
    }

    public void decreaseReCommentCount() {
        reCommentCount--;
    }
}
