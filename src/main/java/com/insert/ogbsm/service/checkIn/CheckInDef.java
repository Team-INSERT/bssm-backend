package com.insert.ogbsm.service.checkIn;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.checkIn.repo.CheckInRepo;
import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.YearSemester;
import com.insert.ogbsm.domain.room.repo.RoomRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CheckInDef {
    private final CheckInRepo checkInRepo;
    private final RoomRepo roomRepo;

    public Long checkIn(Long userId) {
        Collection<Set<Long>> userIdSet = Collections.singleton(Collections.singleton(userId));
        Room room = roomRepo.findByYearSemesterAndRoomMate_RoomMateIdsIn(new YearSemester(), userIdSet)
                .orElseThrow(() -> new BsmException(ErrorCode.NOT_YOUR_ROOM));

        if (!room.getRoomMate().getRoomMateIds().contains(userId)) {
            throw new BsmException(ErrorCode.NOT_YOUR_ROOM);
        }

        Optional<CheckIn> checkIn = checkInRepo.findTodayCheckIn(LocalDate.now().atStartOfDay(), userId);

        if (checkIn.isPresent()) {
            throw new BsmException(ErrorCode.ALREADY_CHECKIN);
        }

        CheckIn newCheckIn = CheckIn.builder()
                .roomId(room.getId())
                .userId(userId)
                .checkInTime(LocalDateTime.now())
                .build();

        return checkInRepo.save(newCheckIn).getId();
    }

}