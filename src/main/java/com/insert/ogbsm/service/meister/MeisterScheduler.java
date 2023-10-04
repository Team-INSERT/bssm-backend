package com.insert.ogbsm.service.meister;

import com.insert.ogbsm.domain.meister.MeisterData;
import com.insert.ogbsm.domain.meister.repository.MeisterDataRepository;
import com.insert.ogbsm.domain.user.Student;
import com.insert.ogbsm.domain.user.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeisterScheduler {

    private final StudentRepo studentRepo;
    private final MeisterDataRepository meisterDataRepository;
    private final MeisterDataProvider meisterDataProvider;

    @Scheduled(cron = "0 0 0 * * ?")
    private void updateAllStudentsInfo() {
        // 재학중인 학생 리스트 불러오기
        List<Student> studentList = studentRepo.findByGradeNot(0);
        List<MeisterData> meisterDataList = meisterDataRepository.findAll();

        studentList.forEach(student -> {
            // 이미 정보가 저장되어있는 학생인지 확인
            Optional<MeisterData> data = meisterDataList.stream()
                    .filter(meisterData -> meisterData.getStudentId().equals(student.getStudentId()))
                    .findFirst();

            MeisterData meisterData = data.orElseGet(
                    () -> meisterDataProvider.findOrElseCreateMeisterData(student)
            );

            // 정보 업데이트
            meisterDataProvider.getAndUpdateMeisterData(meisterData);
            try {
                // 마이스터 인증제 서버에 부담이 가지않도록 1초 지연
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
