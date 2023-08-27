package com.insert.ogbsm.domain.comment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Comment comment;

    private int likeCount;

    public ReComment(String detail, Comment comment) {
        this.detail = detail;
        this.comment = comment;
    }
}
