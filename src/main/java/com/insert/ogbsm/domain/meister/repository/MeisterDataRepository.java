package com.insert.ogbsm.domain.meister.repository;

import com.insert.ogbsm.domain.meister.MeisterData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MeisterDataRepository extends JpaRepository<MeisterData, String>, MeisterDataDao {

    Optional<MeisterData> findByStudentIdAndModifiedAtGreaterThan(String studentId, LocalDateTime today);
}
