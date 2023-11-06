package com.insert.ogbsm.domain.checkIn.repo;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CheckInRepo extends JpaRepository<CheckIn, Long>, CheckInDao {
    CheckIn findByUserIdAndCheckInTime(Long userId, LocalDateTime checkInTime);

    Optional<CheckIn> findByUserIdAndCheckInTimeAndRoomId(Long userId, LocalDateTime checkInTime, Long roomId);
}
