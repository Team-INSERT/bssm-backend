package com.insert.ogbsm.presentation.ber.dto;

import com.insert.ogbsm.domain.ber.Ber;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BerRes {
    private final Long id;
    private final Integer berNumber;
    private final LocalDate reservation;
    private final BerUserSimple user;

    public BerRes(Ber ber) {
        this.id = ber.getId();
        this.berNumber = ber.getBerNumber();
        this.reservation = ber.getReservationDate();
        this.user = new BerUserSimple(ber.getReservationUser());
    }
}
