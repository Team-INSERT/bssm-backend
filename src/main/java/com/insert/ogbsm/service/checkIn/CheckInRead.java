package com.insert.ogbsm.service.checkIn;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.checkIn.repo.CheckInRepo;
import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.repo.RoomRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.checkIn.dto.CheckInRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.insert.ogbsm.domain.room.type.DormitoryType.A;
import static com.insert.ogbsm.domain.room.type.DormitoryType.B;

@Service
@RequiredArgsConstructor
public class CheckInRead {
    private final CheckInRepo checkInRepo;
    private final RoomRepo roomRepo;

    public boolean getMyCheckIn(Long userId) {
        return checkInRepo.findTodayCheckIn(LocalDate.now().atStartOfDay(), userId).isPresent();
    }

    public List<CheckInRes> getCheckIn(String dormitoryType) {
        List<Room> room = switch (dormitoryType) {
            case "A" -> checkInRepo.findAllTodayCheckIn(A);
            case "B" -> checkInRepo.findAllTodayCheckIn(B);
            default -> checkInRepo.findAllTodayCheckIn();
        };

        return room.stream()
                .map(room1 -> new CheckInRes(room1, checkInRepo.findCheckInByRoomId(room1.getId()))).toList();
    }

    public CheckInRes getMyRoom(Long userId) {
        CheckIn myCheckIn = checkInRepo.findByUserId(userId).orElseThrow();
        CheckIn checkIn = checkInRepo.findByUserId(myCheckIn.getUserId())
                .orElseThrow(() -> new BsmException(ErrorCode.ALREADY_CHECKIN));


        Room room = roomRepo.findById(checkIn.getRoomId())
                .orElseThrow(() -> new BsmException(ErrorCode.NOT_YOUR_ROOM));


        return new CheckInRes(room, checkInRepo.findCheckInByRoomId(checkIn.getRoomId()));
    }
}
