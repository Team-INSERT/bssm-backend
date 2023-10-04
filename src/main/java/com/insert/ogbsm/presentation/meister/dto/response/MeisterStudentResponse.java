package com.insert.ogbsm.presentation.meister.dto.response;

import com.insert.ogbsm.domain.user.Student;
import com.querydsl.core.annotations.QueryProjection;

public record MeisterStudentResponse(
        int grade,
        int classNo,
        int studentNo,
        String name
) {

    public MeisterStudentResponse(Student student) {
        this(student.getGrade(), student.getClassNo(), student.getStudentNo(), student.getName());
    }

    @QueryProjection
    public MeisterStudentResponse {
    }
}
