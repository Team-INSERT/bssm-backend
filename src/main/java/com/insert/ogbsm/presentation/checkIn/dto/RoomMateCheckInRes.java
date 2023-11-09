package com.insert.ogbsm.presentation.checkIn.dto;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.user.dto.UserSimpleWithNameRes;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoomMateCheckInRes {

    UserSimpleWithNameRes userSimpleWithNameRes;
    LocalDateTime checkInTime;

    public RoomMateCheckInRes(CheckIn checkIn, User user) {
        this.userSimpleWithNameRes = new UserSimpleWithNameRes(user);
        this.checkInTime = checkIn.getCheckInTime();
    }
}
