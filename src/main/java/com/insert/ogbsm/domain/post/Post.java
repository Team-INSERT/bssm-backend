package com.insert.ogbsm.domain.post;

import com.insert.ogbsm.domain.common.CreatedAt;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.values.CodeReview;
import com.insert.ogbsm.domain.post.values.LostFound;
import com.insert.ogbsm.domain.post.values.Project;
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

    private Long writerId;

    @Embedded
    private CodeReview codeReview;

    @Embedded
    private LostFound lostFound;

    @Embedded
    private Project project;

    private int likeCount = 0;

    public Post(String title, Category category, String content, Long writerId) {
        this.writerId = writerId;
        this.title = title;
        this.category = category;
        this.content = content;
    }

    public void update(Post post) {
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.content = post.getContent();
        this.codeReview = post.getCodeReview();
        this.lostFound = post.getLostFound();
        this.project = post.getProject();
    }

    public void setCodeReview(CodeReview codeReview) {
        this.codeReview = codeReview;
    }

    public void setLostFound(LostFound lostFound) {
        this.lostFound = lostFound;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setIdForUpdate(Long id) {
        this.id = id;
    }

    public void decreaseLike() {
        likeCount--;
    }

    public void increaseLike() {
        likeCount++;
    }
}
