package com.insert.ogbsm.domain.ber.repo;

import com.insert.ogbsm.presentation.ber.dto.BerRes;

import java.time.LocalDate;
import java.util.List;

public interface BerDao {
    List<BerRes> findByReservationDate(LocalDate reservationDate);
}
