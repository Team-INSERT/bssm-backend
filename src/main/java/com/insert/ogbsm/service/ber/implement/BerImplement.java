package com.insert.ogbsm.service.ber.implement;

import com.insert.ogbsm.domain.ber.Ber;
import com.insert.ogbsm.domain.ber.repo.BerRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.ber.dto.BerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BerImplement {
    private final BerRepo berRepo;

    public Long append(Ber ber) {
        return berRepo.save(ber).getId();
    }

    public Ber read(Long berId) {
        return berRepo.findById(berId)
                .orElseThrow(() -> new BsmException(ErrorCode.Ber_Not_Found));
    }

    public List<BerRes> findByReservationDate(LocalDate reservation) {
        return berRepo.findByReservationDate(reservation);
    }

    public void remove(Ber ber) {
        berRepo.delete(ber);
    }
}
