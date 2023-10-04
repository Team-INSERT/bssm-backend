package com.insert.ogbsm.service.validation;

import com.insert.ogbsm.domain.ber.repo.BerRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.ber.dto.BerReserveReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class BerValidation {

    private final BerRepo berRepo;

    public void executeReserve(BerReserveReq berReserveReq, Long userId) {
        checkAlreadyReserved(berReserveReq.getBerNumber(), berReserveReq.getReservationTime());
        checkUserAlreadyReservedSameTime(berReserveReq.getReservationTime(), userId);
        checkReservationTime(berReserveReq.getReservationTime());

    }

    public void executeCancel(Long berReserveUserId, Long userId) {
        if (!berReserveUserId.equals(userId)) {
            throw new BsmException(ErrorCode.NOT_SAME_USER);
        }
    }

    private void checkAlreadyReserved(int berNumber, LocalDate reservationTime) {
        berRepo.findByBerNumberAndReservationDate(berNumber, reservationTime)
                .ifPresent(ber -> {
                    throw new BsmException(ErrorCode.Ber_Already_Reserved);
                });
    }

    private void checkUserAlreadyReservedSameTime(LocalDate reservationTime, Long userId) {
        berRepo.findByReservationDateAndReservationUserId(reservationTime, userId)
                .ifPresent(ber -> {
                    throw new BsmException(ErrorCode.Ber_User_Already_Reserved_Same_Time);
                });
    }

    private void checkReservationTime(LocalDate reservationTime) {
        LocalDate now = LocalDate.now();
        TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
        LocalDate ThisSunday = now.with(fieldUS, 1);
        LocalDate ThisThursDay = now.with(fieldUS, 5);

        if (reservationTime.isBefore(now)) {
            throw new BsmException(ErrorCode.Ber_Reservation_Time_Before_Now);
        }


        if (reservationTime.isAfter(ThisSunday) && reservationTime.isBefore(ThisThursDay)) {
            return;
        }
        throw new BsmException(ErrorCode.Ber_Reservation_Time_Not_Monday_To_Sunday);
    }

}
