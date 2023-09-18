package com.insert.ogbsm.domain.calender;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Min(1)
    @Max(3)
    private Short grade;

    @Min(1)
    @Max(4)
    private Short classNumber;

    private String color;

    @Enumerated(EnumType.STRING)
    private Type type;

    private Long userId;

    public Calender(String title, int priority, Date date, String color, Type type, Short grade, Short classNumber) {
        this.title = title;
        this.priority = priority;
        this.date = date;
        this.color = color;
        this.type = type;
        this.grade = grade;
        this.classNumber = classNumber;
    }

    public void updateUserId(Long userId) {
        this.userId = userId;
    }

    public void updatePriority(int priority) {
        this.priority = priority;
    }
}
