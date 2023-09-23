package com.insert.ogbsm.presentation.ber.dto;


import com.insert.ogbsm.domain.user.User;
import lombok.Getter;

@Getter
public class BerUserSimple {

    private final Long id;

    private final String name;

    private final String nickname;

    private final String profileImage;

    private final Short grade;

    private final Short class_number;

    private final Short student_number;

    public BerUserSimple(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfile_image();
        this.grade = user.getGrade();
        this.class_number = user.getClass_number();
        this.student_number = user.getStudent_number();
    }
}