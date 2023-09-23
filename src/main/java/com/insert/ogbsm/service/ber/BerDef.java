package com.insert.ogbsm.service.ber;

import com.insert.ogbsm.domain.ber.Ber;
import com.insert.ogbsm.domain.ber.repo.BerRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.ber.dto.BerReserveReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BerDef {

    private final BerRepo berRepo;

    public Long berReserve(BerReserveReq berReserveReq) {

        User user = SecurityUtil.getCurrentUserWithLogin();

        berRepo.findByBerNumberAndReservationDate(berReserveReq.getBerNumber(), berReserveReq.getReservationTime())
                .ifPresent(ber -> {
                    throw new BsmException(ErrorCode.Ber_Already_Reserved);
                });

        berRepo.findByReservationDateAndReservationUser(berReserveReq.getReservationTime(), user)
                .ifPresent(ber -> {
                    throw new BsmException(ErrorCode.Ber_User_Already_Reserved_Same_Time);
                });

        Ber ber = Ber.builder()
                .berNumber(berReserveReq.getBerNumber())
                .reservation(berReserveReq.getReservationTime())
                .reservationUser(user)
                .build();

        return berRepo.save(ber).getId();
    }

    public void berCancel(Long berId) {
        Ber ber = berRepo.findById(berId)
                .orElseThrow(() -> new BsmException(ErrorCode.Ber_Not_Found));

        User user = SecurityUtil.getCurrentUserWithLogin();

        if (ber.getReservationUser().getId() != user.getId()) {
            throw new BsmException(ErrorCode.FORBIDDEN);
        }

        berRepo.delete(ber);
    }

}
