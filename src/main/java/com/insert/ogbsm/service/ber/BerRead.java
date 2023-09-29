package com.insert.ogbsm.service.ber;

import com.insert.ogbsm.domain.ber.repo.BerRepo;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.presentation.ber.dto.BerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BerRead {

    private final BerRepo berRepo;
    private final UserRepo userRepo;

    public List<BerRes> getBer(LocalDate reservation) {
        return berRepo.findByReservationDate(reservation);
    }
}
