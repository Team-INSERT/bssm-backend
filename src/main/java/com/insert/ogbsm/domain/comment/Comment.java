package com.insert.ogbsm.domain.comment;

import com.insert.ogbsm.domain.post.Post;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY,
    cascade = CascadeType.REMOVE)
    @JoinColumn
    private Post post;

    private int likeCount;

    //TODO 댓글 작성자

    //TODO 나중에 댓글에 작성자 추가함
    public Comment(String detail, Post post) {
        this.detail = detail;
        this.post = post;
        this.hasReComment = false;
        this.likeCount = 0;
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
