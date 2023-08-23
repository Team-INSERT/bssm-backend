package com.insert.ogbsm.domain.post;

import com.insert.ogbsm.domain.common.CreatedAt;
import com.insert.ogbsm.domain.post.category.Category;
import jakarta.persistence.*;

@Entity
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
}
