package com.insert.ogbsm.service.checkIn.implement;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.checkIn.repo.CheckInRepo;
import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.repo.RoomRepo;
import com.insert.ogbsm.domain.user.repo.UserRepo;
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
public class CheckInImplement {
    private final RoomRepo roomRepo;
    private final CheckInRepo checkInRepo;
    private final UserRepo userRepo;

    public Room readByUserId(Long userId) {
        return roomRepo.findByUserId(userId)
                .orElseThrow(() -> new BsmException(ErrorCode.ROOM_NOT_FOUND));
    }

    public boolean readDidICheckInToday(Long userId) {
        return checkInRepo.findTodayCheckIn(LocalDate.now().atStartOfDay(), userId).isPresent();
    }

    public List<CheckInRes> readByDormType(String dormitoryType) {
        List<Room> room = switch (dormitoryType) {
            case "A" -> checkInRepo.findAllTodayCheckIn(A);
            case "B" -> checkInRepo.findAllTodayCheckIn(B);
            default -> checkInRepo.findAllTodayCheckIn();
        };

        return room.stream()
                .map(room1 -> new CheckInRes(room1, checkInRepo.findCheckInByRoomId(room1.getId()),
                        userRepo.findByIdIn(room1.getRoomMate().getRoomMateIds()))).toList();
    }


    public CheckInRes readRoomById(Long userId) {
        Room myRoom = readByUserId(userId);
        List<CheckIn> checkInList = checkInRepo.findCheckInByRoomId(myRoom.getId());

        return new CheckInRes(myRoom, checkInList,userRepo.findByIdIn(myRoom.getRoomMate().getRoomMateIds()));
    }
}
