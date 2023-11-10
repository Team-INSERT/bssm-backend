package com.insert.ogbsm.service.room.business;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.YearSemester;
import com.insert.ogbsm.domain.room.repo.RoomRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.room.dto.AllocateRoomReq;
import com.insert.ogbsm.presentation.room.dto.RoomRes;
import com.insert.ogbsm.service.room.implement.RoomImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomBusiness {
    private final RoomImplement roomImplement;

    @Transactional
    public void allocateRoom(AllocateRoomReq allocateRoomReq, Long userId) {
        Optional<Room> room = roomImplement.readByDate(allocateRoomReq.roomNumber(), allocateRoomReq.dormitoryType());

        if (roomImplement.isFirstRoomMember(room)) {
            Room newRoom = roomImplement.append(allocateRoomReq);
            roomImplement.updateRoomMate(newRoom, userId);
        }

        roomImplement.updateRoomMate(room.get(), userId);
    }

    public boolean setAllocateRoomPublic() {
        Room.AllowAllocate = !Room.AllowAllocate;
        return Room.AllowAllocate;
    }

    public Room getMyRoom(Long userId) {
        return roomImplement.readMyRoom(userId);
    }
}
