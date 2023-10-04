package com.insert.ogbsm.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @Column(length = 10)
    private String studentId;

    @Column(columnDefinition = "year")
    private int enrolledAt;

    @Column(columnDefinition = "INT(1) UNSIGNED")
    private int grade;

    @Column(columnDefinition = "INT(1) UNSIGNED")
    private int classNo;

    @Column(columnDefinition = "INT(2) UNSIGNED")
    private int studentNo;

    @Column(length = 8)
    private String name;

    @Column(length = 32)
    private String email;

    @Builder
    public Student(String studentId, int enrolledAt, int grade, int classNo, int studentNo, String name, String email) {
        this.studentId = studentId;
        this.enrolledAt = enrolledAt;
        this.grade = grade;
        this.classNo = classNo;
        this.studentNo = studentNo;
        this.name = name;
        this.email = email;
    }

    public void update(int enrolledAt, int grade, int classNo, int studentNo) {
        this.enrolledAt = enrolledAt;
        this.grade = grade;
        this.classNo = classNo;
        this.studentNo = studentNo;
    }

}

