package com.insert.ogbsm.service.checkIn.implement;

import static com.insert.ogbsm.domain.room.type.DormitoryType.A;
import static com.insert.ogbsm.domain.room.type.DormitoryType.B;

import com.insert.ogbsm.domain.checkIn.repo.CheckInRepo;
import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.repo.RoomRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.checkIn.dto.CheckInRes;
import com.insert.ogbsm.presentation.checkIn.dto.IsCheckInRes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return checkInRepo.findTodayCheckIn(userId).isPresent();
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


    public IsCheckInRes readRoomById(Long userId) {
        Room myRoom = readByUserId(userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new BsmException(ErrorCode.USER_NOT_FOUND));
        boolean isCheckin = checkInRepo.findTodayCheckIn(userId).isPresent();

        return new IsCheckInRes(myRoom, user,isCheckin);
    }
}
