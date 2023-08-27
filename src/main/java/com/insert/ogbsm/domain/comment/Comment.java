package com.insert.ogbsm.domain.comment;

import com.insert.ogbsm.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detail;

    private boolean hasReComment;

    private Long postId;

    private int likeCount;

    private Long userId;

    public Comment(String detail, Long postId, Long userId) {
        this.detail = detail;
        this.postId = postId;
        this.hasReComment = false;
        this.likeCount = 0;
        this.userId = userId;
    }

    public void update(String detail) {
        this.detail = detail;
    }

    public void addLike() {
        hasReComment = true;
        likeCount++;
    }

    public void deleteLike() {
        likeCount--;
    }

    public void newReComment() {
        hasReComment = true;
    }

    public void noReComment() {
        hasReComment = false;
    }
}
