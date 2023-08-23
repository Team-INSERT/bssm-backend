package com.insert.ogbsm.domain.post;

import com.insert.ogbsm.domain.common.CreatedAt;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.values.CodeReview;
import com.insert.ogbsm.domain.post.values.LostFound;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends CreatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(30)")
    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String content;

    //TODO ADD INFO OF WRITER

    @Embedded
    private CodeReview codeReview;

    @Embedded
    private LostFound lostFound;

    public Post(String title, Category category, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
    }

    public void setCodeReview(CodeReview codeReview) {
        this.codeReview = codeReview;
    }

    public void setLostFound(LostFound lostFound) {
        this.lostFound = lostFound;
    }
}
