package com.insert.ogbsm.domain.user.repo;

import com.insert.ogbsm.domain.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, String> {
    Optional<Student> findByEmail(String email);
    List<Student> findByGradeNot(int i);
    Optional<Student> findByGradeAndClassNoAndStudentNo(int grade, int classNo, int studentNo);
}
