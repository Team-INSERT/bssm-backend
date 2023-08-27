package com.insert.ogbsm.domain.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User{

    @Column()
    @Min(2021)
    private Long enroll;

    @Column()
    @Min(1)
    @Max(3)
    private Long grade;

    @Column()
    @Min(1)
    @Max(4)
    private Long class_number;

    @Column()
    @Min(1)
    @Max(16)
    private Long student_number;
}
