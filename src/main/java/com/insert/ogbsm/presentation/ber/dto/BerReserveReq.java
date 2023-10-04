package com.insert.ogbsm.presentation.ber.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BerReserveReq {
    private int berNumber;
    private LocalDate reservationTime;
    private String reservationUsersName;
}
