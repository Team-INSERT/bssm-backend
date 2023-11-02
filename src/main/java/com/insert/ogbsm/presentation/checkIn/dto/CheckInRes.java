package com.insert.ogbsm.presentation.checkIn.dto;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class CheckInRes {
    String roomNumber;
    DormitoryType dormitoryType;
    List<Long> roomMates;
    List<RoomMateCheckInRes> checkInList;

    public CheckInRes(Room room, List<CheckIn> checkIn) {
        this.roomNumber = room.getRoomNumber();
        this.dormitoryType = room.getDormitoryType();
        this.roomMates = room.getRoomMate().getRoomMateIds().stream().toList();

        try {
            this.checkInList = checkIn.stream().map(RoomMateCheckInRes::new).toList();
        } catch (Exception e) {
            this.checkInList = Collections.emptyList();
        }
    }
}
