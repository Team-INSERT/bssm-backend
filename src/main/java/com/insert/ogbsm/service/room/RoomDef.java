package com.insert.ogbsm.service.room;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.YearSemester;
import com.insert.ogbsm.domain.room.repo.RoomRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.room.dto.AllocateRoomReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomDef {
    private final RoomRepo roomRepo;

    @Transactional
    public Long allocateRoom(AllocateRoomReq allocateRoomReq, Long userId) {
        Optional<Room> room = roomRepo.findByYearSemesterAndRoomNumberAndDormitoryType(
                new YearSemester(), allocateRoomReq.roomNumber(), allocateRoomReq.dormitoryType());

        Optional<Room> existRoom = roomRepo.findByUserId(userId);
        if(existRoom.isPresent()) throw new BsmException(ErrorCode.ALREADY_HAS_ROOM);

        if (room.isEmpty()) {
            Room newRoom = Room.builder()
                    .roomNumber(allocateRoomReq.roomNumber())
                    .dormitoryType(allocateRoomReq.dormitoryType())
                    .build();
            newRoom.getRoomMate().addRoomMate(userId);
            return roomRepo.save(newRoom).getId();
        }
        room.get().getRoomMate().addRoomMate(userId);

        return room.get().getId();
    }

    public boolean setAllocateRoomPublic() {
        Room.AllowAllocate = !Room.AllowAllocate;
        return Room.AllowAllocate;
    }


}
