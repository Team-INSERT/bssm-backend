package com.insert.ogbsm.domain.checkIn.repo;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import java.util.List;
import java.util.Optional;

public interface CheckInDao {
    Optional<CheckIn> findTodayCheckIn(Long userId);

    List<CheckIn> findCheckInByRoomId(Long roomId);

    List<Room> findAllTodayCheckIn(DormitoryType dormitoryType);

    List<Room> findAllTodayCheckIn();
}