package com.insert.ogbsm.domain.room.repo;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.YearSemester;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.presentation.room.dto.RoomRes;

import java.util.List;
import java.util.Optional;

public interface RoomDao {

    List<RoomRes> findAllByYearSemester();

    Room findByUserId(Long userId);

    Optional<Room> findByAllData(String roomNumber, DormitoryType dormitoryType, YearSemester yearSemester);

}
