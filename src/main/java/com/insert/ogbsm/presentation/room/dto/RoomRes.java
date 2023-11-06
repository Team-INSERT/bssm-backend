package com.insert.ogbsm.presentation.room.dto;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.presentation.user.dto.UserSimpleWithNameRes;
import lombok.Getter;

import java.util.List;

@Getter
public class RoomRes {
    private final String roomNumber;
    private final DormitoryType dormitoryType;
    private final List<UserSimpleWithNameRes> roommates;

    public RoomRes(Room room, List<UserSimpleWithNameRes> roommates) {
        this.roomNumber = room.getRoomNumber();
        this.dormitoryType = room.getDormitoryType();
        this.roommates = roommates;
    }
}
