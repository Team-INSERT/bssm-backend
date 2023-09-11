package com.insert.ogbsm.domain.like;

import com.insert.ogbsm.domain.common.CreatedAt;
import com.insert.ogbsm.domain.like.type.Type;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes extends CreatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private Long partyId;

    public Likes(Long userId, Type type, Long partyId) {
        this.userId = userId;
        this.type = type;
        this.partyId = partyId;
    }
}
