package com.insert.ogbsm.domain.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeReview extends PostBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String githubLink;

    public CodeReview(String title, String content, String githubLink) {
        super(title, content);
        this.githubLink = githubLink;
    }
}
