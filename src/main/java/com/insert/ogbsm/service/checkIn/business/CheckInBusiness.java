package com.insert.ogbsm.service.checkIn.business;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.presentation.checkIn.dto.CheckInRes;
import com.insert.ogbsm.service.checkIn.implement.CheckInImplement;
import com.insert.ogbsm.service.checkIn.implement.CheckInValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.insert.ogbsm.domain.room.type.DormitoryType.A;
import static com.insert.ogbsm.domain.room.type.DormitoryType.B;

@Service
@RequiredArgsConstructor
public class CheckInBusiness {
    private final CheckInImplement checkInImplement;
    private final CheckInValidation checkInValidation;

    public Long checkIn(Long userId) {
        Room room = checkInImplement.readByUserId(userId);
        checkInValidation.mustNotCheckedIn(userId);
        return checkInValidation.appendCheckIn(room, userId);
    }

    public boolean readMyCheckIn(Long userId) {
        return checkInImplement.readDidICheckInToday(userId);
    }

    public List<CheckInRes> getCheckIn(String dormitoryType) {
        return checkInImplement.readByDormType(dormitoryType);
    }

    public CheckInRes getMyRoom(Long userId) {
        return checkInImplement.readRoomById(userId);
    }

}