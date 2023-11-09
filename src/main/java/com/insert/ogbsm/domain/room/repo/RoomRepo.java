package com.insert.ogbsm.domain.room.repo;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.YearSemester;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RoomRepo extends JpaRepository<Room, Long>, RoomDao {
    Optional<Room> findByYearSemesterAndRoomNumberAndDormitoryType(YearSemester yearSemester, String roomNumber, DormitoryType dormitoryType);
    Optional<Room> findByYearSemesterAndRoomMate_RoomMateIdsIn(YearSemester yearSemester, Collection<Set<Long>> roomMateIds);
}
