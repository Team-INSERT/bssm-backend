package com.insert.ogbsm.service.room;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.repo.RoomRepo;
import com.insert.ogbsm.presentation.room.dto.AllocateRoomReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomDef {
    private final RoomRepo roomRepo;

    public Long allocateRoom(AllocateRoomReq allocateRoomReq, Long userId) {

//        if (!Room.AllowAllocate) {
//            throw new BsmException(ErrorCode.Not_ALLOW_DATE);
//        }

        Room room = Room.builder()
                .roomNumber(allocateRoomReq.roomNumber())
                .dormitoryType(allocateRoomReq.dormitoryType())
                .build();

        room.getRoomMate().addRoomMate(userId);

        return roomRepo.save(room).getId();
    }

    public boolean setAllocateRoomPublic() {
        Room.AllowAllocate = !Room.AllowAllocate;
        return Room.AllowAllocate;
    }


}
