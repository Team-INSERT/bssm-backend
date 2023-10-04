package com.insert.ogbsm.presentation.ber.dto;

import com.insert.ogbsm.domain.ber.Ber;
import com.insert.ogbsm.domain.user.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BerRes {
    private final Long id;
    private final int berNumber;
    private final LocalDate reservation;
    private final String reservationUsersName;
    private final BerUserSimpleRes user;

    public BerRes(Ber ber, User user) {
        this.id = ber.getId();
        this.berNumber = ber.getBerNumber();
        this.reservation = ber.getReservationDate();
        this.reservationUsersName = ber.getReservationUsersName();
        this.user = new BerUserSimpleRes(user);
    }
}
