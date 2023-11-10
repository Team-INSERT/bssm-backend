package com.insert.ogbsm.service.ber.business;

import com.insert.ogbsm.domain.ber.Ber;
import com.insert.ogbsm.presentation.ber.dto.BerReadRes;
import com.insert.ogbsm.presentation.ber.dto.BerRes;
import com.insert.ogbsm.service.ber.implement.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BerBusiness {

    private final BerImplement berImplement;
    private final BerValidation berValidation;

    public Long berReserve(Ber ber) {
        berValidation.executeReserve(ber);
        return berImplement.append(ber);
    }

    public void berCancel(Long berId, Long userId) {
        Ber ber = berImplement.read(berId);

        berValidation.executeCancel(ber.getReservationUserId(), userId);
        berImplement.remove(ber);
    }

    @Transactional(readOnly = true)
    public BerReadRes getBer(LocalDate reservation) {
        List<BerRes> bersFindByDate = berImplement.findByReservationDate(reservation);

        return new BerReadRes(bersFindByDate);
    }
}
