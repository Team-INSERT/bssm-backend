package com.insert.ogbsm.domain.ber.repo;

import com.insert.ogbsm.domain.ber.Ber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BerRepo extends JpaRepository<Ber, Long>, BerDao {

    Optional<Ber> findByBerNumberAndReservationDate(Integer berNumber, LocalDate reservationTime);

    Optional<Ber> findByReservationDateAndReservationUserId(LocalDate reservationDate, Long reservationUserId);

}
