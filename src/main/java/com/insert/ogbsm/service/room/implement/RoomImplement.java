package com.insert.ogbsm.service.room.implement;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.YearSemester;
import com.insert.ogbsm.domain.room.repo.RoomRepo;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.room.dto.AllocateRoomReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomImplement {
    private final RoomRepo roomRepo;

    public Optional<Room> readByDate(String roomNumber, DormitoryType dormitoryType) {
        return roomRepo.findByYearSemesterAndRoomNumberAndDormitoryType(
                new YearSemester(), roomNumber, dormitoryType
        );
    }

    public boolean isFirstRoomMember(Optional<Room> room) {
        return room.isEmpty();
    }

    public Room append(AllocateRoomReq allocateRoomReq) {
        Room room = Room.builder()
                .roomNumber(allocateRoomReq.roomNumber())
                .dormitoryType(allocateRoomReq.dormitoryType())
                .build();

        return roomRepo.save(room);
    }

    public void updateRoomMate(Room room, Long userId) {
        room.getRoomMate().addRoomMate(userId);
    }

    public Room readMyRoom(Long userId) {
        return roomRepo.findByUserId(userId)
                .orElseThrow(() -> new BsmException(ErrorCode.ROOM_NOT_FOUND));
    }
}
