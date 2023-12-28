package com.insert.ogbsm.presentation.ber.dto;

import com.insert.ogbsm.domain.ber.Ber;
import com.insert.ogbsm.domain.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BerRes berRes = (BerRes) o;
        return berNumber == berRes.berNumber && Objects.equals(id, berRes.id) && Objects.equals(reservation, berRes.reservation) && Objects.equals(reservationUsersName, berRes.reservationUsersName) && Objects.equals(user.getId(), berRes.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, berNumber, reservation, reservationUsersName, user);
    }
}
