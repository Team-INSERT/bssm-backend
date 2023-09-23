package com.insert.ogbsm.domain.ber.repo;

import com.insert.ogbsm.domain.ber.Ber;
import com.insert.ogbsm.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BerRepo extends JpaRepository<Ber, Long> {

    Optional<Ber> findByBerNumberAndReservationDate(Integer berNumber, LocalDate reservationTime);

    Optional<Ber> findByReservationDateAndReservationUser(LocalDate reservationDate, User reservationUser);


    List<Ber> findByReservationDate(LocalDate reservation);

}
