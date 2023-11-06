package com.insert.ogbsm.presentation.checkIn.dto;

import com.insert.ogbsm.domain.room.type.DormitoryType;

public record CheckInReq(DormitoryType dormitoryType, String RoomNumber) {
}
