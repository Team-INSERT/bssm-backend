package com.insert.ogbsm.service.user.implement;

import com.insert.ogbsm.domain.user.Student;
import com.insert.ogbsm.domain.user.repo.StudentRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentImplement {
    private final StudentRepo studentRepo;


    public Student readByGradeClassNumber(int grade, int classNo, int studentNo) {
        return studentRepo.findByGradeAndClassNoAndStudentNo(grade, classNo, studentNo)
                .orElseThrow(() -> new BsmException(ErrorCode.STUDENT_NOT_FOUND));
    }
}
