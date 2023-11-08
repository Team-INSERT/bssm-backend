package com.insert.ogbsm.presentation.checkIn.dto;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.user.dto.UserSimpleWithNameRes;
import java.util.Objects;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class CheckInRes {
    String roomNumber;
    DormitoryType dormitoryType;
    List<UserSimpleWithNameRes> roomMates;
    List<RoomMateCheckInRes> checkInList;

    public CheckInRes(Room room, List<CheckIn> checkIn, List<User> users) {
        this.roomNumber = room.getRoomNumber();
        this.dormitoryType = room.getDormitoryType();
        this.roomMates = users.stream().map(UserSimpleWithNameRes::new).toList();
//        this.roomMates = room.getRoomMate().getRoomMateIds().stream().toList();

        try {
            this.checkInList = checkIn.stream().map(checkIn1 -> new RoomMateCheckInRes(checkIn1,
                    users.stream().filter(user -> Objects.equals(user.getId(), checkIn1.getUserId())).toList().get(0))).toList();
        } catch (Exception e) {
            this.checkInList = Collections.emptyList();
        }
    }

}
