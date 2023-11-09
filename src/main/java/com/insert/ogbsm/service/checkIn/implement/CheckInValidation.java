package com.insert.ogbsm.service.checkIn.implement;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.checkIn.repo.CheckInRepo;
import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CheckInValidation {
    private final CheckInRepo checkInRepo;

    public void mustNotCheckedIn(Long userId) {
        Optional<CheckIn> checkIn = checkInRepo.findTodayCheckIn(LocalDate.now().atStartOfDay(), userId);

        if (checkIn.isPresent()) {
            throw new BsmException(ErrorCode.ALREADY_CHECKIN);
        }
    }

    public Long appendCheckIn(Room room, Long userId) {
        CheckIn newCheckIn = CheckIn.builder()
                .roomId(room.getId())
                .userId(userId)
                .checkInTime(LocalDateTime.now())
                .build();

        return checkInRepo.save(newCheckIn).getId();
    }
}
