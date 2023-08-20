package com.insert.ogbsm.domain.post;

import com.insert.ogbsm.domain.common.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PostBase extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    // ToDo (생성자에도 추가해야함)
    // private User writer;

    public PostBase(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostBase update(String title, String content) {
        this.title = title;
        this.content = content;

        return this;
    }
}
