package com.insert.ogbsm.service.room;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.YearSemester;
import com.insert.ogbsm.domain.room.repo.RoomRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.room.dto.AllocateRoomReq;
import com.insert.ogbsm.presentation.room.dto.RoomRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomDef {
    private final RoomRepo roomRepo;

    @Transactional
    public Long allocateRoom(AllocateRoomReq allocateRoomReq, Long userId) {

        Optional<Room> room = roomRepo.findByYearSemesterAndRoomNumberAndDormitoryType(
                new YearSemester(), allocateRoomReq.roomNumber(), allocateRoomReq.dormitoryType());


        if (room.isEmpty()) {
            Room newRoom = Room.builder()
                    .roomNumber(allocateRoomReq.roomNumber())
                    .dormitoryType(allocateRoomReq.dormitoryType())
                    .build();
            newRoom.getRoomMate().addRoomMate(userId);
            return roomRepo.save(newRoom).getId();
        }
        System.out.println(room.get().getRoomMate().getRoomMateIds());
        room.get().getRoomMate().addRoomMate(userId);

        return room.get().getId();
    }

    public boolean setAllocateRoomPublic() {
        Room.AllowAllocate = !Room.AllowAllocate;
        return Room.AllowAllocate;
    }

    @Transactional(readOnly = true)
    public List<RoomRes> getRoom() {
        return roomRepo.findAllByYearSemester();
    }
    public Room getMyRoom(Long id) {
        return roomRepo.findByUserId(id)
                .orElseThrow(() -> new BsmException(ErrorCode.ROOM_NOT_FOUND));
    }

}
