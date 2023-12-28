package com.insert.ogbsm.presentation.ber.dto;

import com.insert.ogbsm.domain.ber.Ber;
import lombok.Getter;

import java.time.LocalDate;

public record BerReserveReq(
    int berNumber,
    LocalDate reservationTime,
    String reservationUsersName
) {
    public Ber to(Long userId) {
        return Ber.builder()
                .berNumber(berNumber)
                .reservationDate(reservationTime)
                .reservationUsersName(reservationUsersName)
                .reservationUserId(userId)
                .build();
    }
}
