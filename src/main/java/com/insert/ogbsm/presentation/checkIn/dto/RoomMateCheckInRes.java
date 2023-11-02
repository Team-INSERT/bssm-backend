package com.insert.ogbsm.presentation.checkIn.dto;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoomMateCheckInRes {

    Long userId;
    LocalDateTime checkInTime;

    public RoomMateCheckInRes(CheckIn checkIn) {
        System.out.println(checkIn.getId());
        this.userId = checkIn.getUserId();
        this.checkInTime = checkIn.getCheckInTime();
    }
}
