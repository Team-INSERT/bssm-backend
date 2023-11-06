package com.insert.ogbsm.presentation.room.dto;

import com.insert.ogbsm.domain.room.type.DormitoryType;


public record AllocateRoomReq(DormitoryType dormitoryType, String roomNumber) {
}
