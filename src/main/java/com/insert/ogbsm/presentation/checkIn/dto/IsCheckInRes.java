package com.insert.ogbsm.presentation.checkIn.dto;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.user.dto.UserSimpleWithNameRes;
import lombok.Getter;

@Getter
public class IsCheckInRes {
    String roomNumber;
    DormitoryType dormitoryType;
    UserSimpleWithNameRes user;
    Boolean isCheckin;

    public IsCheckInRes(Room room, User user, boolean isCheckin) {
        this.roomNumber = room.getRoomNumber();
        this.dormitoryType = room.getDormitoryType();
        this.user = new UserSimpleWithNameRes(user);
        this.isCheckin = isCheckin;

    }
}
