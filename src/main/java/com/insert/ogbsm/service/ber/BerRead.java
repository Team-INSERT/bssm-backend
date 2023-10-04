package com.insert.ogbsm.service.ber;

import com.insert.ogbsm.domain.ber.repo.BerRepo;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.presentation.ber.dto.BerReadRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BerRead {

    private final BerRepo berRepo;
    private final UserRepo userRepo;

    public BerReadRes getBer(LocalDate reservation) {
        return new BerReadRes(berRepo.findByReservationDate(reservation));
    }
}
