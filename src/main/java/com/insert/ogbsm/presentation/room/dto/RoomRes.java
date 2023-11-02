package com.insert.ogbsm.presentation.room.dto;

import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.presentation.user.dto.UserSimpleWithNameRes;
import lombok.Getter;

import java.util.List;

@Getter
public class RoomRes {
    private final String roomNumber;
    private final DormitoryType dormitoryType;
    private final List<UserSimpleWithNameRes> roommates;

    public RoomRes(String roomNumber, DormitoryType dormitoryType, List<UserSimpleWithNameRes> roommates) {
        this.roomNumber = roomNumber;
        this.dormitoryType = dormitoryType;
        this.roommates = roommates;
    }
}
