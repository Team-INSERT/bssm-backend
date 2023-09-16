package com.insert.ogbsm.domain.calender;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int priority;

    @Embedded
    private Date date;

    private Long color;

    private Type type;

    private Long userId;

    public Calender(String title, int priority, Date date, Long color, Type type) {
        this.title = title;
        this.priority = priority;
        this.date = date;
        this.color = color;
        this.type = type;
    }

    public void updateUserId(Long userId) {
        this.userId = userId;
    }
}
