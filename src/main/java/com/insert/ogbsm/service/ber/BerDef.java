package com.insert.ogbsm.service.ber;

import com.insert.ogbsm.domain.ber.Ber;
import com.insert.ogbsm.domain.ber.repo.BerRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.ber.dto.BerReserveReq;
import com.insert.ogbsm.service.validation.BerValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BerDef {

    private final BerRepo berRepo;
    private final BerValidation berValidation;

    public Long berReserve(BerReserveReq berReserveReq, Long userId) {

        berValidation.executeReserve(berReserveReq, userId);

        Ber ber = Ber.builder()
                .berNumber(berReserveReq.getBerNumber())
                .reservation(berReserveReq.getReservationTime())
                .reservationUserId(userId)
                .build();

        return berRepo.save(ber).getId();
    }

    public void berCancel(Long berId, Long userId) {
        Ber ber = berRepo.findById(berId)
                .orElseThrow(() -> new BsmException(ErrorCode.NOT_SAME_USER));

        berValidation.executeCancel(ber.getReservationUserId(), userId);

        berRepo.delete(ber);
    }

}
