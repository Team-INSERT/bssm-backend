package com.insert.ogbsm.domain.checkIn.repo;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.presentation.checkIn.dto.CheckInRes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CheckInDao {
    Optional<CheckIn> findTodayCheckIn(LocalDateTime localDate, Long userId);

    List<CheckInRes> findAllTodayCheckIn(DormitoryType dormitoryType);

    List<CheckInRes> findAllTodayCheckIn();
}